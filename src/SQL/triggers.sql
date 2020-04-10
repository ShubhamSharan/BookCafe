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

CREATE TRIGGER quantity_update before insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE checkQuantity();

--bellow threshold emails
CREATE OR REPLACE FUNCTION LastMonthSales(isbn_in varchar(10))
RETURNS INTEGER AS $$
BEGIN
	RETURN(
	SELECT sum(item_quantity) as quantity_out
	from shipment_confirmed
	where public.shipment_confirmed = LastMonthSales.isbn_in
	and public.shipment_confirmed.order_id
		in (select public.shopping_cart.order_id
			from public.shopping_cart
			where public.shopping_cart.shipment_placement_date > Now() - 31 --Feb glitch
		   ));
END;
$$
LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION pubReq()
RETURNS trigger AS $$
BEGIN
		UPDATE book
		SET last_request_date = Now()
		,request_approved = FALSE
		,requested_quantity = LastMonthSales(NEW.isbn)
		WHERE isbn = NEW.isbn AND quantity < 10;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER publisher_request_update after insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE pubReq();