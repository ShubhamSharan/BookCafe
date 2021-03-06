--This is the view that is created to get sales by genre
drop materialized view CopiesSoldByGenre;
CREATE MATERIALIZED VIEW CopiesSoldByGenre AS
	SELECT
		genre,
		sum(item_quantity)::numeric(15,2) as copies,
		sum(unit_price * item_quantity)::numeric(15,2) as sales
		FROM (public.shipment_confirmed inner join public.genre on public.shipment_confirmed.isbn = public.genre.isbn) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
		GROUP BY public.genre.genre;
--refresh before showcasing the view
REFRESH MATERIALIZED VIEW CopiesSoldByGenre;
SELECT * FROM CopiesSoldByGenre;
--This view is created to get view for sales by author
DROP materialized view CopiesSoldByAuthor;
CREATE MATERIALIZED VIEW CopiesSoldByAuthor AS
	SELECT
		author_name,
		sum(item_quantity)::numeric(15,2) as copies,
		sum(unit_price * item_quantity)::numeric(15,2) as sales
		FROM (public.shipment_confirmed inner join public.author on public.shipment_confirmed.isbn = public.author.isbn) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
		GROUP BY public.author.author_id, public.author.author_name;
		--refresh before showcasing the view
REFRESH MATERIALIZED VIEW CopiesSoldByGenre;
SELECT * FROM CopiesSoldByAuthor

--Sales by this month only
drop materialized view SalesVsExpThisMonth;
CREATE MATERIALIZED VIEW SalesVsExpThisMonth AS
SELECT
	public.shipment_confirmed.isbn,
	public.book.book_name,
	public.book.publisher_id,
	public.book.percent_to_publisher,
	sum(item_quantity) as Copies_Sold,
	sum(item_quantity * unit_price)::numeric(20,2) as sales,
	sum((item_quantity * unit_price)*percent_to_publisher) as publisherscut,
	sum((item_quantity * unit_price) - ((item_quantity * unit_price)*percent_to_publisher)) as profit
	FROM (public.shipment_confirmed inner join public.shopping_cart on public.shipment_confirmed.order_id = public.shopping_cart.order_id) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
	WHERE public.shopping_cart.shipment_placement_date >= date_trunc('month', CURRENT_DATE)
	GROUP BY public.shipment_confirmed.isbn,public.shopping_cart.shipment_placement_date,public.book.isbn
	ORDER BY
	public.shopping_cart.shipment_placement_date;

REFRESH MATERIALIZED VIEW SalesVsExpThisMonth;
SELECT * FROM SalesVsExpThisMonth;

--Sales since day one
drop materialized view SalesVsExpAllMonth;
CREATE MATERIALIZED VIEW SalesVsExpAllMonth AS
SELECT
	public.shipment_confirmed.isbn,
	public.book.book_name,
	public.book.publisher_id,
	to_char(public.shopping_cart.shipment_placement_date, 'YYYY-MM') as date,
	public.book.percent_to_publisher,
	sum(item_quantity) as Copies_Sold,
	sum(item_quantity * unit_price)::numeric(20,2) as sales,
	sum((item_quantity * unit_price)*percent_to_publisher) as publisherscut,
	sum((item_quantity * unit_price) - ((item_quantity * unit_price)*percent_to_publisher)) as profit
	FROM (public.shipment_confirmed inner join public.shopping_cart on public.shipment_confirmed.order_id = public.shopping_cart.order_id) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
	GROUP BY public.shipment_confirmed.isbn,public.shopping_cart.shipment_placement_date,public.book.isbn
	ORDER BY
	date;
--refresh before as always to ensure up to date
REFRESH MATERIALIZED VIEW SalesVsExpAllMonth;
