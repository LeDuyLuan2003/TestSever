CREATE DATABASE IF NOT EXISTS webbanhang;
USE webbanhang;

-- Bảng categories
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Bảng products
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    avatar VARCHAR(255),
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Bảng users
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(10) UNIQUE,
    provider VARCHAR(50)
);

-- Bảng roles
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(250)
);

-- Bảng user_role (Bảng liên kết giữa users và roles)
CREATE TABLE user_role (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Bảng orders
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

-- Bảng order_details
CREATE TABLE order_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    product_id BIGINT,
    order_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE SET NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Bảng images
CREATE TABLE images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255),
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);


INSERT INTO categories (name) VALUES ('Electronics');
INSERT INTO categories (name) VALUES ('Fashion');
INSERT INTO categories (name) VALUES ('Home Appliances');

INSERT INTO products (name, price, description, avatar, category_id) VALUES
('Smartphone', 500.0, 'High-quality smartphone', 'https://cdn.tgdd.vn/Products/Images/42/197228/huawei-p30-pro-1-600x600.jpg', 1),
('Laptop', 1000.0, 'Powerful laptop for professionals', 'https://drive.gianhangvn.com/image/laptop-dell-inspiron-5567-laptop-43-2182489j20085.jpg', 1),
('T-Shirt', 20.0, 'Comfortable cotton T-shirt', 'https://dosi-in.com/file/detailed/9/Basic-T-shirt-Black.jpg?w=1000&h=1000&fit=fill&fm=webp', 2);

INSERT INTO users (username, password, email, phone, provider) VALUES
('khanhml001', '$2a$10$BvKEVkSPHFMwY0d/3KM6T.FJESytWrVhJUdpt0Df5xqXgeCZzJOqq', 'khanhml001@gmail.com', '0123456789', 'local'),
('khanhml002', '$2a$10$BvKEVkSPHFMwY0d/3KM6T.FJESytWrVhJUdpt0Df5xqXgeCZzJOqq', 'khanhml002@gmail.com', '0987654321', 'local');
-- pass: khanhml001

INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator role'),
('USER', 'Regular user role');

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1),  -- John Doe là Admin
(2, 2);  -- Jane Doe là User

INSERT INTO orders (customer_name) VALUES
('John Doe'),
('Jane Doe');

INSERT INTO order_details (quantity, product_id, order_id) VALUES
(2, 1, 1),  -- John Doe mua 2 sản phẩm đầu tiên
(1, 2, 1),  -- John Doe mua 1 sản phẩm thứ hai
(3, 3, 2);  -- Jane Doe mua 3 sản phẩm thứ ba

INSERT INTO images (url, product_id) VALUES
('https://cdn.tgdd.vn/Products/Images/42/197228/huawei-p30-pro-1-600x600.jpg', 1),  -- Ảnh smartphone
('https://drive.gianhangvn.com/image/laptop-dell-inspiron-5567-laptop-43-2182489j20085.jpg', 2),  -- Ảnh laptop
('https://dosi-in.com/file/detailed/9/Basic-T-shirt-Black.jpg?w=1000&h=1000&fit=fill&fm=webp', 3);  -- Ảnh T-shirt







