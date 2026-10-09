CREATE DATABASE IF NOT EXISTS fashion_store;
USE fashion_store;

CREATE TABLE IF NOT EXISTS categories (
  category_id INT NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (category_id),
  UNIQUE KEY category_name (category_name)
);

CREATE TABLE IF NOT EXISTS products (
  product_id INT NOT NULL AUTO_INCREMENT,
  category_id INT NOT NULL,
  product_name VARCHAR(150) NOT NULL,
  description TEXT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  color VARCHAR(50) NOT NULL,
  image VARCHAR(255) NOT NULL,
  brand VARCHAR(100) NOT NULL,
  PRIMARY KEY (product_id),
  KEY category_id (category_id),
  CONSTRAINT products_ibfk_1 FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE IF NOT EXISTS product_sizes (
  product_size_id INT NOT NULL AUTO_INCREMENT,
  product_id INT NOT NULL,
  size VARCHAR(10) NOT NULL,
  stock INT NOT NULL DEFAULT 0,
  PRIMARY KEY (product_size_id),
  KEY product_id (product_id),
  CONSTRAINT product_sizes_ibfk_1 FOREIGN KEY (product_id) REFERENCES products (product_id)
);

CREATE TABLE IF NOT EXISTS users (
  user_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL,
  password VARCHAR(255) NOT NULL,
  phone VARCHAR(15) NOT NULL,
  address VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(100) NOT NULL,
  pincode VARCHAR(10) NOT NULL,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id),
  UNIQUE KEY email (email)
);

CREATE TABLE IF NOT EXISTS cart (
  cart_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (cart_id),
  UNIQUE KEY user_id (user_id),
  CONSTRAINT cart_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS cart_items (
  cart_item_id INT NOT NULL AUTO_INCREMENT,
  cart_id INT NOT NULL,
  product_id INT NOT NULL,
  product_size_id INT NOT NULL,
  quantity INT NOT NULL DEFAULT 1,
  PRIMARY KEY (cart_item_id),
  KEY cart_id (cart_id),
  KEY product_id (product_id),
  KEY product_size_id (product_size_id),
  CONSTRAINT cart_items_ibfk_1 FOREIGN KEY (cart_id) REFERENCES cart (cart_id),
  CONSTRAINT cart_items_ibfk_2 FOREIGN KEY (product_id) REFERENCES products (product_id),
  CONSTRAINT cart_items_ibfk_3 FOREIGN KEY (product_size_id) REFERENCES product_sizes (product_size_id)
);

CREATE TABLE IF NOT EXISTS orders (
  order_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  total_amount DECIMAL(10,2) NOT NULL,
  order_status VARCHAR(30) NOT NULL DEFAULT 'PLACED',
  order_date TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (order_id),
  KEY user_id (user_id),
  CONSTRAINT orders_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS order_items (
  order_item_id INT NOT NULL AUTO_INCREMENT,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  product_size_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (order_item_id),
  KEY order_id (order_id),
  KEY product_id (product_id),
  KEY product_size_id (product_size_id),
  CONSTRAINT order_items_ibfk_1 FOREIGN KEY (order_id) REFERENCES orders (order_id),
  CONSTRAINT order_items_ibfk_2 FOREIGN KEY (product_id) REFERENCES products (product_id),
  CONSTRAINT order_items_ibfk_3 FOREIGN KEY (product_size_id) REFERENCES product_sizes (product_size_id)
);

INSERT IGNORE INTO categories (category_id, category_name) VALUES
(1, 'Men'),
(2, 'Women'),
(3, 'Kids'),
(4, 'Footwear'),
(5, 'Accessories');
