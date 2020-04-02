DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TYPE bankaccounttype AS (
	account_name    varchar(255),
    account_number  numeric(16,0),
    expirydate   date
);

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
	user_type boolean, --is publisher
    name nametype,
    email varchar(255) not null check (email like '%@%'),
    password varchar(255) not null,
	address addresstype not null,
    bank_account bankaccounttype not null check((bank_account).expirydate > now()),
    primary key (user_id)
);

CREATE TABLE public.user_phone
(
	user_id varchar(10) not null,
	phonenumber varchar(10) not null,
	primary key (user_id,phonenumber),
	foreign key (user_id) references public.user on delete cascade
);

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

CREATE TABLE public.pub_phone
(
	publisher_id varchar(10) not null,
	phonenumber varchar(10) not null,
	primary key (publisher_id,phonenumber),
	foreign key (publisher_id) references public.publisher on delete cascade
);


CREATE TABLE public.book
(
    isbn varchar(10) not null,
    quantity integer not null default 0,
    book_name varchar(255) not null,
    number_of_pages integer not null,
    unit_price numeric(6,2) not null,
    date_of_publish date not null check (date_of_publish < now()),
    primary key (isbn)
);


CREATE TABLE public.author
(
	author_id varchar(10) not null,
	author_name varchar(255) not null,
	isbn varchar(10) not null,
	primary key (author_id,isbn),
	foreign key (isbn) references public.book
);


CREATE TABLE public.genre
(
	isbn varchar(10) not null,
	genre varchar(255) check (genre in ('fiction', 'non-fiction', 'fantasy', 'educational','crime','cooking','adult','programming','self help'))
);

CREATE TABLE public.published
(
	publisher_id varchar(10) not null,
	isbn varchar(10) not null,
	percent_to_publisher numeric(4,3) not null check (percent_to_publisher < 1) ,
	requested_quantity varchar(10) not null,
	request_approved boolean,
	primary key (isbn),
	foreign key(isbn) references public.book on delete cascade,
	foreign key(publisher_id) references public.publisher on delete cascade
);


CREATE TABLE public.shopping_cart
(
    order_id varchar(10) not null,
	shipment_address addressType,
	shipement_placement_date date,
    primary key (order_id)
);

CREATE TABLE public.order_tracker
(
	order_id varchar(10) not null,
	user_id varchar(10) not null,
	shipment_recieved_date date,
	primary key(order_id),
	foreign key (order_id) references public.shopping_cart on delete cascade,
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



























