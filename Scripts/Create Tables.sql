create table CATEGORY(
cat_id int AUTO_INCREMENT NOT NULL,
cat_name varchar(50) NOT NULL,
is_active bit NOT NULL,
PRIMARY KEY (cat_id)
);

create table SUBCATEGORY(
subcat_id int AUTO_INCREMENT NOT NULL,
cat_id int NOT NULL,
cat_name varchar(50) NOT NULL,
is_active bit NOT NULL,
PRIMARY KEY (subcat_id),
CONSTRAINT FK_cat_subcat FOREIGN KEY (cat_id) REFERENCES CATEGORY(cat_id)
);

create table CUSTOMER(
customer_id int AUTO_INCREMENT NOT NULL,
customer_name varchar(100) NOT NULL,
phone_no char(11) NOT NULL,
address varchar(200) NOT NULL,
PRIMARY KEY (customer_id)
);

create table PRODUCT(
product_id int AUTO_INCREMENT NOT NULL,
product_name varchar(50) NOT NULL,
description varchar(200),
image varchar(150),
code char(13) NOT NULL,
subcat_id int NOT NULL,
price decimal(18,2) NOT NULL,
is_active bit NOT NULL,
PRIMARY KEY (product_id),
CONSTRAINT FK_subcat_product FOREIGN KEY (subcat_id) REFERENCES SUBCATEGORY(subcat_id)
);

create table QUOTATION(
quotation_id int AUTO_INCREMENT NOT NULL,
customer_id int NOT NULL,
order_date datetime NOT NULL,
PRIMARY KEY (quotation_id),
CONSTRAINT FK_customer_quotation FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customer_id)
);

create table QUOTATIONLIST(
quotation_id int AUTO_INCREMENT NOT NULL,
product_id int NOT NULL,
quantity int NOT NULL,
total_price decimal(18,2) NOT NULL,
CONSTRAINT FK_quotation_list FOREIGN KEY (quotation_id) REFERENCES QUOTATION(quotation_id),
CONSTRAINT FK_product_list FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

create table STOCK(
stock_id int AUTO_INCREMENT NOT NULL,
product_id int NOT NULL,
quantity int NOT NULL,
PRIMARY KEY (stock_id),
CONSTRAINT FK_product_stock FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

create table SALE(
sale_id int AUTO_INCREMENT NOT NULL,
quotation_id int NOT NULL,
PRIMARY KEY (sale_id),
CONSTRAINT FK_quotation_sale FOREIGN KEY (quotation_id) REFERENCES QUOTATION(quotation_id)
);

create table USERS(
user_id int AUTO_INCREMENT NOT NULL,
user_name varchar(50) NOT NULL,
is_active bit NOT NULL,
password varchar(100) NOT NULL,
PRIMARY KEY (user_id)
);
