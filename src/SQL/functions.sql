DROP FUNCTION IF EXISTS SearchByIsbn(val varchar(10));

CREATE FUNCTION SearchByIsbn(val varchar(10))
	RETURNS TABLE(
		isbn varchar(10),
		quantity integer,
		book_name varchar(255),
		number_of_pages integer,
		unit_price numeric(4,2),
		date_of_publish date) AS $$
		DECLARE
		BEGIN
		RETURN QUERY
			select book.isbn,book.quantity,book.book_name,book.number_of_pages,book.unit_price,book.date_of_publish
			from public.book
			where public.book.isbn LIKE '%SearchByIsbn.val%' OR
			public.book.isbn = val OR
			public.book.isbn LIKE 'SearchByIsbn.val%' OR
			public.book.isbn LIKE '%SearchByIsbn.val';
		END;
$$ LANGUAGE plpgsql;

select * from searchByIsbn('000811613X');
