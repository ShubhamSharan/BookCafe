DROP FUNCTION IF EXISTS SearchByAtomic(val varchar(400), col_nam varchar(400));

CREATE FUNCTION SearchByAtomic(val varchar(400), col_nam varchar(400))
	RETURNS TABLE(
		isbn varchar(10),
		quantity integer,
		book_name varchar(255),
		number_of_pages integer,
		unit_price numeric(6,2),
		date_of_publish date,
		publisher_name text,
		authors text,
		genres text
	) AS $$
		DECLARE
		BEGIN
		RETURN QUERY EXECUTE format('
			select book.isbn,book.quantity,book.book_name,book.number_of_pages,book.unit_price,book.date_of_publish,CONCAT((public.publisher.name).first_name,'' '',(public.publisher.name).last_name) as "publisher_name", string_agg(DISTINCT public.author.author_name, '', '') as authors,string_agg(DISTINCT public.genre.genre, '', '') as genres
			from
			((public.book inner join public.author on public.author.isbn = public.book.isbn) inner join public.publisher on public.publisher.publisher_id = public.book.publisher_id) inner join public.genre on public.genre.isbn = public.book.isbn
			where
			%s ~ ''%s''
			group by book.isbn,public.publisher.publisher_id',col_nam,val);
		END;
$$ LANGUAGE plpgsql;

select * from SearchByAtomic('a','book.isbn');


CREATE OR REPLACE FUNCTION ShoppingCartsList(user_id varchar(10))
	RETURNS TABLE(
		order_id varchar(10),
		address_name varchar(255),
		city varchar(255),
		state varchar(4),
		zip varchar(15)
	)
	AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select public.shopping_cart.order_id as order_id,
			(public.shopping_cart.shipment_address).address_name as address_name,
			(public.shopping_cart.shipment_address).city as city,
			(public.shopping_cart.shipment_address).state as state,
			(public.shopping_cart.shipment_address).zip as zip
			from public.shopping_cart
			where shopping_cart.user_id = ShoppingCartsList.user_id and shopping_cart.order_id not in (select public.shipment_confirmed.order_id from public.shipment_confirmed)
			;
		END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION Shipment(user_id varchar(10))
	RETURNS TABLE(
		orderId varchar(10),
		addressName varchar(255),
		city varchar(255),
		state varchar(4),
		zip varchar(15),
		shipmentPlacementDate date
	)
	AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select public.shopping_cart.order_id as orderId,
			(public.shopping_cart.shipment_address).address_name as addressName,
			(public.shopping_cart.shipment_address).city as city,
			(public.shopping_cart.shipment_address).state as state,
			(public.shopping_cart.shipment_address).zip as zip,
			public.shopping_cart.shipement_placement_date as shipmentPlacementDate
			from public.shopping_cart
			where shopping_cart.user_id = Shipment.user_id and  shopping_cart.order_id in (select public.shipment_confirmed.order_id from public.shipment_confirmed)
			;
		END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS UserDets(userid varchar(10));

CREATE OR REPLACE FUNCTION userDets(userid varchar(10))
	RETURNS TABLE(
		user_id varchar(10),
		first_name varchar(255),
		middle_name varchar(255),
		last_name varchar(255),
		email varchar(255),
		address text
	)
	AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select
			public.user.user_id as user_id,
			(public.user.name).first_name as first_name,
			(public.user.name).middle_name as middle_name,
			(public.user.name).last_name as last_name,
			public.user.email as email,
			CONCAT((public.user.address).address_name,', ',(public.user.address).city,', ',(public.user.address).state,', ',(public.user.address).zip) as address
			from public.user
			where public.user.user_id ~ userDets.userid;
		END;
$$ LANGUAGE plpgsql;
select * from userDets('')

CREATE OR REPLACE FUNCTION pubDets(idval varchar(10))
	RETURNS TABLE(
		publisher_id varchar(10),
		first_name varchar(255),
		middle_name varchar(255),
		last_name varchar(255),
		email varchar(255),
		address text
	)
	AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select
			public.publisher.publisher_id as publisher_id,
			(public.publisher.name).first_name as first_name,
			(public.publisher.name).middle_name as middle_name,
			(public.publisher.name).last_name as last_name,
			public.publisher.email as email,
			CONCAT((public.publisher.address).address_name,', ',(public.publisher.address).city,', ',(public.publisher.address).state,', ',(public.publisher.address).zip) as address
			from public.publisher
			where public.publisher.publisher_id ~ pubDets.idval;
		END;
$$ LANGUAGE plpgsql;



