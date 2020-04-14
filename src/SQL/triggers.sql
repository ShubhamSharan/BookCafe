-- confirms shipments
CREATE OR REPLACE FUNCTION addPlacement()
RETURNS trigger AS $$
BEGIN
	   UPDATE shopping_cart
	   SET shipment_placement_date = Now() WHERE order_id = NEW.order_id;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';
--after shipment is added the shipment_placement_date is set
DROP TRIGGER IF EXISTS shipment_update ON public.shipment_confirmed;
CREATE TRIGGER shipment_update after insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE addPlacement();


-- Reduces quantity
CREATE OR REPLACE FUNCTION checkQuantity()
RETURNS trigger AS $$
BEGIN
		UPDATE book
		SET quantity = (quantity - NEW.item_quantity) WHERE isbn = NEW.isbn;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

--before confirmed the books are subtracted and secured for that specific user. Its like they are in his hand and no one else can buy it but he himself hasn't commited 100%
DROP TRIGGER IF EXISTS quantity_update ON public.shipment_confirmed;
CREATE TRIGGER quantity_update before insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE checkQuantity();

-- determines last months sales to get the quantity for reorder
CREATE OR REPLACE FUNCTION LastMonthSales(isbn_in varchar(10))
RETURNS INTEGER AS $$
BEGIN
	RETURN(
	SELECT sum(item_quantity) as quantity_out
	FROM public.shipment_confirmed
	where public.shipment_confirmed.isbn = LastMonthSales.isbn_in
	and public.shipment_confirmed.order_id
		in (select public.shopping_cart.order_id
			from public.shopping_cart
			where public.shopping_cart.shipment_placement_date >= date_trunc('month', CURRENT_DATE-30)
		   ));
END;
$$
LANGUAGE 'plpgsql';

Select * from LastMonthSales('997105933B');
-- this is crucial as it gets triggered when the quantity dips bellow threshold before insertion, why before cause we already validate stock prior while adding to cart.
CREATE OR REPLACE FUNCTION pubReq()
RETURNS trigger AS $$
BEGIN
update book set
	request_approved = FALSE,
	last_request_date = Now(),
	requested_quantity = LastMonthSales(NEW.isbn)
	WHERE isbn = NEW.isbn AND quantity < 10;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

--trigger creation
DROP TRIGGER IF EXISTS publisher_request_update on shipment_confirmed;
CREATE TRIGGER publisher_request_update before insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE pubReq();