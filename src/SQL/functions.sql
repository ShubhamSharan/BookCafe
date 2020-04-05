DROP FUNCTION IF EXISTS UserList();
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




CREATE FUNCTION UserList()
	RETURNS TABLE(
		user_id varchar(10),
		fullname text,
		email varchar(255),
		address text
	) AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select public.user.user_id,CONCAT((public.user.name).first_name,' ',(public.user.name).last_name) as "fullname", public.user.email, CONCAT((public.user.address).address_name,' ',(public.user.address).city,' ',(public.user.address).state,' ',(public.user.address).zip) as "address"
			from public.user;
		END;
$$ LANGUAGE plpgsql;


select * from SearchByAtomic('10','book.publisher_id');
-- select * from UserList()










