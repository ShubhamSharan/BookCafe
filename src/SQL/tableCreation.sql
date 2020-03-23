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

CREATE TABLE public.publisher
(
    publisher_id varchar(10) not null,
    name nametype,
    email varchar(255) not null,
    password varchar(255) not null,
    address addressType not null,
    phone_number varchar(16) array[3],
    bank_account bankaccounttype not null,
    primary key (publisher_id)
);

CREATE TABLE public.user
(
    user_id varchar(10) not null,
    name nametype,
    email varchar(255) not null,
    password varchar(255) not null,
    address addressType not null,
    phone_number varchar(16) array[3],
    bank_account bankaccounttype not null,
    primary key (user_id)
);



CREATE TABLE public.bookstore
(
    isbn varchar(10) not null,
    quantity integer not null default 0,
    book_name varchar(255) not null,
    author_names name[20],
    genre varchar(100)[20],
    publisher_id varchar(10) not null,
    number_of_pages integer not null,
    unit_price numeric(4,2) not null,
    precent_to_publisher numeric(3,0) not null,
    date_of_publish date not null,
    primary key (isbn),
	foreign key (publisher_id) references public.publisher
);

CREATE TABLE public.shipment
(
    shipment_id varchar(10) not null,
    isbn varchar(10) not null,
    -- TODO: shipment_status varchar(255) not null,
    -- TODO: Function requested_quantity integer not null,
    provided_quantity integer,
    shipment_placement_date date not null,
    shipment_recieved_date date,
    primary key (shipment_id),
    foreign key (isbn) references public.bookstore
);


CREATE TABLE public.shopping_cart
(
    order_id varchar(10) not null,
    user_id varchar(10) not null,
    isbn varchar(10) not null,
    quantity integer,
    status_of_purchase varchar(20),
    primary key (order_id),
    foreign key (isbn) references public.bookstore,
	foreign key (user_id) references public.user
);































