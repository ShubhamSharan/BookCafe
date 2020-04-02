
 DROP FUNCTION IF EXISTS SearchByIsbn(val varchar(10));
 DROP FUNCTION IF EXISTS SearchByName(val varchar(10));


 CREATE FUNCTION SearchByIsbn(val varchar(10))
 	RETURNS TABLE(
 		isbn varchar(10),
 		quantity integer,
 		book_name varchar(255),
 		number_of_pages integer,
 		unit_price numeric(6,2),
 		date_of_publish date,
 		first_name varchar(255),
 		last_name varchar(255),
 		authors text,
 		genres text
 	) AS $$
 		DECLARE
 		BEGIN
 		RETURN QUERY
 			select book.isbn,book.quantity,book.book_name,book.number_of_pages,book.unit_price,book.date_of_publish,CONCAT((public.publisher.name).first_name,' ',(public.publisher.name).last_name) as "publisher_name", string_agg(public.author.author_name, ', ') as authors,string_agg(public.genre.genre, ', ') as genres
 			from
 			((public.book inner join public.author on public.author.isbn = public.book.isbn) inner join public.publisher on public.publisher.publisher_id = public.book.publisher_id) left join public.genre on public.genre.isbn = public.book.isbn
 			where
 			public.book.isbn = SearchByIsbn.val OR
 			public.book.isbn ~ SearchByIsbn.val
 			group by book.isbn,public.publisher.publisher_id;
 		END;
 $$ LANGUAGE plpgsql;

 CREATE FUNCTION SearchByName(val varchar(400))
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
 		RETURN QUERY
 			select book.isbn,book.quantity,book.book_name,book.number_of_pages,book.unit_price,book.date_of_publish,CONCAT((public.publisher.name).first_name,' ',(public.publisher.name).last_name) as "publisher_name", string_agg(public.author.author_name, ', ') as authors,string_agg(public.genre.genre, ', ') as genres
 			from
 			((public.book inner join public.author on public.author.isbn = public.book.isbn) inner join public.publisher on public.publisher.publisher_id = public.book.publisher_id) left join public.genre on public.genre.isbn = public.book.isbn
 			where
 			public.book.book_name = SearchByName.val OR
 			public.book.book_name ~ SearchByName.val
 			group by book.isbn,public.publisher.publisher_id;
 		END;
 $$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS SearchByAtomic(val varchar(400),col_nam text );

CREATE FUNCTION SearchByAtomic(val varchar(400), col_nam text)
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
			select book.isbn,book.quantity,book.book_name,book.number_of_pages,book.unit_price,book.date_of_publish,CONCAT((public.publisher.name).first_name,''. '',(public.publisher.name).last_name) as "publisher_name", string_agg(public.author.author_name, '', '') as authors,string_agg(public.genre.genre, '', '') as genres
			from 
			((public.book inner join public.author on public.author.isbn = public.book.isbn) inner join public.publisher on public.publisher.publisher_id = public.book.publisher_id) left join public.genre on public.genre.isbn = public.book.isbn
			where
			'||col_nam||' ~ ''||SearchByAtomic.val||''
			group by book.isbn,public.publisher.publisher_id');
		END;
$$ LANGUAGE plpgsql;

select * from searchByAtomic('Adventure','public.book.book_name');
