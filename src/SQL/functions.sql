CREATE FUNCTION searchTableAtomic(val varchar(10), columnName varchar(255))
	RETURNS TABLE(
		isbn varchar(10),
		quantity integer,
		book_name varchar(255),
		author_id varchar(10)[20],
		genre varchar(100)[20],
		publisher_id varchar(10),
		number_of_pages integer,
		unit_price numeric(4,2),
		precent_to_publisher numeric(3,0),
		date_of_publish date)
	return table(
		select * from public.bookstore
		where public.bookstore.columnName LIKE '%'+val+'%' OR bookstore.columnName = val;
	);
