drop materialized view CopiesSoldByGenre;
CREATE MATERIALIZED VIEW CopiesSoldByGenre AS
	SELECT
		genre,
		sum(item_quantity)::numeric(15,2) as copies,
		sum(unit_price * item_quantity)::numeric(15,2) as sales
		FROM (public.shipment_confirmed inner join public.genre on public.shipment_confirmed.isbn = public.genre.isbn) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
		GROUP BY public.genre.genre;

REFRESH MATERIALIZED VIEW CopiesSoldByGenre;
SELECT * FROM CopiesSoldByGenre;

DROP materialized view CopiesSoldByAuthor;
CREATE MATERIALIZED VIEW CopiesSoldByAuthor AS
	SELECT
		author_name,
		sum(item_quantity)::numeric(15,2) as copies,
		sum(unit_price * item_quantity)::numeric(15,2) as sales
		FROM (public.shipment_confirmed inner join public.author on public.shipment_confirmed.isbn = public.author.isbn) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
		GROUP BY public.author.author_id, public.author.author_name;
REFRESH MATERIALIZED VIEW CopiesSoldByGenre;
SELECT * FROM CopiesSoldByAuthor

drop materialized view SalesByMonth;
CREATE MATERIALIZED VIEW SalesByMonth AS
SELECT
	public.shipment_confirmed.isbn,
	public.book.book_name,
	sum(item_quantity) as Copies_Sold,
	sum(item_quantity * unit_price)::numeric(20,2) as sales
	FROM (public.shipment_confirmed inner join public.shopping_cart on public.shipment_confirmed.order_id = public.shopping_cart.order_id) inner join public.book on public.shipment_confirmed.isbn = public.book.isbn
	WHERE public.shopping_cart.shipment_placement_date >= date_trunc('month', CURRENT_DATE)
	GROUP BY public.shipment_confirmed.isbn,public.shopping_cart.shipment_placement_date,public.book.isbn
	ORDER BY
	public.shopping_cart.shipment_placement_date;

REFRESH MATERIALIZED VIEW SalesByMonth;
SELECT * FROM SalesByMonth;













