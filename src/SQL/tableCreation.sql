--drop everything before recreation
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TYPE bankaccounttype AS (
	account_name    varchar(255),
    account_number  numeric(16,0),
    expirydate   date
);

-- Custom composite data types are used to reduce repetition of code
CREATE TYPE addresstype AS
(
    address_name varchar(255),
    city varchar(255),
    state varchar(4),
    zip varchar(15)
);

CREATE TYPE nametype AS (
    first_name  varchar(255),
    middle_name   varchar(255),
    last_name  varchar(255)
);

CREATE TABLE public.user
(
    user_id varchar(10) not null,
	user_type boolean not null, --is admin or user
    name nametype,
    email varchar(255) not null check (email like '%@%'),
    password varchar(255) not null,
	address addresstype not null,
    bank_account bankaccounttype not null check((bank_account).expirydate > now()),
    primary key (user_id)
); --if expired bank account added don't accept

--mutli value table
CREATE TABLE public.user_phone
(
	user_id varchar(10) not null,
	phonenumber varchar(10) not null,
	primary key (user_id,phonenumber),
	foreign key (user_id) references public.user on delete cascade
);

--Both user and publisher use custom sql data types
CREATE TABLE public.publisher
(
    publisher_id varchar(10) not null,
    name nametype,
    email varchar(255) not null check (email like '%@%'),
    password varchar(255) not null,
    address addresstype not null,
    bank_account bankaccounttype not null check((bank_account).expirydate > now()),
    primary key (publisher_id)
);

--mutli value table
CREATE TABLE public.pub_phone
(
	publisher_id varchar(10) not null,
	phonenumber varchar(10) not null,
	primary key (publisher_id,phonenumber),
	foreign key (publisher_id) references public.publisher on delete cascade
);
--Book item
CREATE TABLE public.book
(
    isbn varchar(10) not null,
    quantity integer not null default 0,
    book_name varchar(355) not null,
    number_of_pages integer not null,
    unit_price numeric(6,2) not null,
    date_of_publish date not null check (date_of_publish < now()),
	percent_to_publisher numeric(4,3) not null check (percent_to_publisher < 1) ,
	requested_quantity integer not null,
	last_request_date date not null,
	request_approved boolean,
	publisher_id varchar(10),
    primary key (isbn),
	foreign key (publisher_id) references public.publisher
);

--serial autogenerates the author id for me
CREATE TABLE public.author
(
	author_id serial unique not null,
	author_name varchar(255) not null,
	isbn varchar(10) not null,
	primary key (author_id,isbn),
	foreign key (isbn) references public.book on delete cascade
);

--mutli value table
CREATE TABLE public.genre
(
	isbn varchar(10) not null,
	genre varchar(255) check (genre in ('fiction', 'kids','non-fiction', 'fantasy', 'educational','crime','cooking','adult','programming','self help')),
	primary key (isbn,genre),
	foreign key (isbn) references public.book on delete cascade
);

--A shopping cart only exists if the user exists
CREATE TABLE public.shopping_cart
(
    order_id varchar(10) not null,
	user_id varchar(10) not null,
	shipment_address addressType,
	shipment_placement_date date,
    primary key (order_id),
	foreign key (user_id) references public.user on delete cascade
);

CREATE TABLE public.shipment_confirmed
(
	isbn varchar(10) not null,
	order_id varchar(10) not null,
	item_quantity integer,
	primary key (isbn, order_id),
	foreign key (isbn) references public.book,
	foreign key (order_id) references public.shopping_cart on delete cascade
);



























