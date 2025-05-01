create table customer
  (
	customer_id bigint not null auto_increment,
	customer_name varchar(200),
    points bigint,
    primary key (customer_id)
);
