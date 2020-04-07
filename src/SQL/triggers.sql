CREATE OR REPLACE FUNCTION addPlacement()
RETURNS trigger AS $$
BEGIN
	   UPDATE public.shopping_cart
	   SET shipment_placement_date = Now() WHERE public.shopping_cart.order_id = NEW.order_id;
	RETURN NEW;
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER shipment_update after insert
on shipment_confirmed
FOR EACH ROW
EXECUTE PROCEDURE addPlacement();