create database pharmacy_database;
use pharmacy_database;

-- Tabla de proveedores
CREATE TABLE suppliers(
	id int primary key auto_increment,
	name varchar(60),
	description varchar(255),
	telephone varchar(20),
	address varchar(60),
	email varchar(255),
	city varchar(60),
	created datetime,
	updated datetime
);

-- Tabla de empleados
CREATE TABLE employees(
	id int primary key,
    full_name varchar(60),
    username varchar(60),
    address varchar(60),
    telephone varchar(20),
    email varchar(255),
    password varchar(255),
    rol varchar(20),
    created datetime,
    updated datetime
);

-- Tabla de clientes
CREATE TABLE customers(
	id int primary key,
    full_name varchar(60),
    address varchar(60),
    telephone varchar(20),
    email varchar(255),
    created datetime,
    updated datetime
);

-- Tabla de categories
CREATE TABLE categories(
	id int primary key auto_increment,
    name varchar(60),
    created datetime,
    updated datetime
);

-- Tabla de compras
CREATE TABLE purchases(
	id int primary key auto_increment,
    total double,
    created datetime,
    supplier_id int,
    employee_id int,
    foreign key (supplier_id) references suppliers (id),
    foreign key (employee_id) references employees (id)
);

-- Tabla de productos
CREATE TABLE products(
	id int primary key auto_increment,
    code int unique,
    name varchar(60),
    description varchar(255),
    unit_price double,
    product_quantity int,
    created datetime,
    updated datetime,
    category_id int,
    foreign key (category_id) references categories (id)
);
-- Tabla de detalles de compra
CREATE TABLE purchase_details(
	id int primary key auto_increment,
    purchase_price double,
    purchase_amount int,
    purchase_subtotal double,
    purchase_id int,
    product_id int,
    foreign key (purchase_id) references purchases (id),
    foreign key (product_id) references products (id)
);

-- Tabla de ventas
CREATE TABLE sales(
	id int primary key auto_increment,
    sale_date datetime,
    total double,
    customer_id int,
    employee_id int,
    foreign key (customer_id) references customers (id),
    foreign key (employee_id) references employees (id)
);

-- Tabla de detalles de venta
CREATE TABLE sale_details(
	id int primary key auto_increment,
    sale_quantity int,
    sale_price double,
    sale_subtotal double,
    product_id int,
    sale_id int,
    foreign key (product_id) references products (id),
    foreign key (sale_id) references sales (id)
);

