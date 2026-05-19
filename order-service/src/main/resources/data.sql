INSERT INTO orders (order_status, price) VALUES
('PENDING', 1200),
('CONFIRMED', 2400),
('SHIPPED', 3500),
('DELIVERED', 4200),
('CANCELLED', 1800),
('PENDING', 5600),
('CONFIRMED', 6100),
('SHIPPED', 7200),
('DELIVERED', 8300),
('CANCELLED', 9400),
('PENDING', 1500),
('CONFIRMED', 2600),
('SHIPPED', 3700),
('DELIVERED', 4800),
('CANCELLED', 5900),
('PENDING', 6100),
('CONFIRMED', 7200),
('SHIPPED', 8300),
('DELIVERED', 9400),
('CANCELLED', 10500);

INSERT INTO order_item (product_id, quantity, order_id) VALUES
(1, 2, 1),
(2, 1, 1),

(3, 1, 2),
(4, 2, 2),

(5, 1, 3),
(6, 3, 3),

(7, 2, 4),
(8, 1, 4),

(9, 1, 5),
(10, 2, 5),

(11, 1, 6),
(12, 4, 6),

(13, 2, 7),
(14, 1, 7),

(15, 3, 8),
(16, 2, 8),

(17, 1, 9),
(18, 2, 9),

(19, 1, 10),
(20, 5, 10),

(2, 1, 11),
(4, 2, 11),

(6, 3, 12),
(8, 1, 12),

(10, 2, 13),
(12, 1, 13),

(14, 4, 14),
(16, 2, 14),

(18, 1, 15),
(20, 2, 15),

(1, 1, 16),
(3, 2, 16),

(5, 1, 17),
(7, 3, 17),

(9, 2, 18),
(11, 1, 18),

(13, 2, 19),
(15, 1, 19),

(17, 4, 20),
(19, 2, 20);