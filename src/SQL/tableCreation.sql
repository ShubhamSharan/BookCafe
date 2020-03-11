
CREATE TYPE bankaccounttype AS (
    account_number  numeric(16,0),
    account_name    text,
    expirydetails   date,
);

CREATE TYPE nametype AS (
    first_name  varchar(255) not null,
    middle_name   varchar(255),
    last_name  varchar(255) not null,
);

CREATE TABLE public.author
(author_id	varchar(10),
 publisher_id	varchar(10),
 name nametype,
 primary key (author_id)
);

CREATE TABLE public.bookstore
(
    isbn varchar(10) not null,
    quantity integer not null default 0,
    book_name varchar(255) not null,
    -- TODO: author_id
    -- TODO: genre
    publisher_id varchar(10) not null,
    number_of_pages integer not null,
    unit_price numeric(4,2) not null,
    precent_to_publisher numeric(3,0) not null,
    date_of_publish date not null,
    primary key (isbn)
);

CREATE TABLE public.shopping_cart
(
    order_id varchar(10) not null,
    user_id varchar(10) not null,
    isbn varchar(10) not null,
    quantity integer,
    status_of_purchase varchar(20),
    primary key (order_id),
    foreign key (isbn) references bookstore
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
    foreign key (isbn) references bookstore
);