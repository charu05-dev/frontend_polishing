USE fashion_store;

-- Point existing products at organized local image paths.
UPDATE products SET image = 'men/classic-shirt.jpg' WHERE product_id = 1;
UPDATE products SET image = 'men/slim-jeans.jpg' WHERE product_id = 2;
UPDATE products SET image = 'men/casual-hoodie.jpg' WHERE product_id = 3;
UPDATE products SET image = 'women/floral-kurti.jpg' WHERE product_id = 4;
UPDATE products SET image = 'women/summer-dress.jpg' WHERE product_id = 5;
UPDATE products SET image = 'women/denim-jacket.jpg' WHERE product_id = 6;
UPDATE products SET image = 'kids/kids-tshirt.jpg' WHERE product_id = 7;
UPDATE products SET image = 'kids/kids-jeans.jpg' WHERE product_id = 8;
UPDATE products SET image = 'footwear/running-shoes.jpg' WHERE product_id = 9;
UPDATE products SET image = 'footwear/casual-sneakers.jpg' WHERE product_id = 10;
UPDATE products SET image = 'accessories/leather-handbag.jpg' WHERE product_id = 11;
UPDATE products SET image = 'accessories/classic-backpack.jpg' WHERE product_id = 12;

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Linen Oxford Shirt', 'Breathable linen-blend oxford shirt for warm-weather layering.', 1699.00, 'White', 'men/linen-oxford.jpg', 'Van Heusen'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Linen Oxford Shirt');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Graphic Crew T-Shirt', 'Soft cotton crew neck tee with a clean graphic print.', 799.00, 'Black', 'men/graphic-tee.jpg', 'HRX'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Graphic Crew T-Shirt');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Slim Chino Trousers', 'Tailored chinos with a comfortable stretch waist.', 1899.00, 'Beige', 'men/chino-trousers.jpg', 'Peter England'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Slim Chino Trousers');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Quilted Bomber Jacket', 'Lightweight quilted bomber for everyday outerwear.', 3299.00, 'Olive', 'men/bomber-jacket.jpg', 'Jack & Jones'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Quilted Bomber Jacket');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Pique Polo T-Shirt', 'Classic pique polo with a structured collar.', 1199.00, 'Navy', 'men/polo-tee.jpg', 'US Polo Assn'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Pique Polo T-Shirt');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 1, 'Utility Cargo Trousers', 'Relaxed cargo trousers with functional side pockets.', 1599.00, 'Olive', 'men/cargo-trousers.jpg', 'Roadster'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Utility Cargo Trousers');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'High-Rise Skinny Jeans', 'Stretch skinny jeans with a high-rise fit.', 2199.00, 'Blue', 'women/skinny-jeans.jpg', 'Levis'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'High-Rise Skinny Jeans');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'Wide Leg Trousers', 'Fluid wide-leg trousers for work and weekend styling.', 1799.00, 'Black', 'women/wide-trousers.jpg', 'AND'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Wide Leg Trousers');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'Knit Crop Top', 'Soft knit crop top with a clean neckline.', 999.00, 'White', 'women/knit-top.jpg', 'Zara'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Knit Crop Top');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'A-Line Midi Dress', 'Flowy midi dress with an easy A-line silhouette.', 1899.00, 'Maroon', 'women/midi-dress.jpg', 'Global Desi'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'A-Line Midi Dress');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'Tailored Blazer', 'Structured blazer designed for polished everyday looks.', 3499.00, 'Beige', 'women/tailored-blazer.jpg', 'Marks & Spencer'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Tailored Blazer');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 2, 'Printed Tunic Top', 'Lightweight printed tunic for easy daytime wear.', 1299.00, 'Green', 'women/printed-tunic.jpg', 'W'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Printed Tunic Top');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 3, 'Kids Colorblock Hoodie', 'Cozy hoodie with a soft inner lining for kids.', 899.00, 'Red', 'kids/kids-hoodie.jpg', 'Max'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Kids Colorblock Hoodie');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 3, 'Kids Court Sneakers', 'Durable everyday sneakers made for active kids.', 1499.00, 'Blue', 'kids/kids-sneakers.jpg', 'Puma'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Kids Court Sneakers');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 4, 'Leather Ankle Boots', 'Stacked-heel ankle boots with a closed rounded toe.', 2799.00, 'Brown', 'footwear/ankle-boots.jpg', 'Metro'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Leather Ankle Boots');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 4, 'Block Heel Sandals', 'Comfortable block-heel sandals for day-to-night outfits.', 1599.00, 'Nude', 'footwear/block-sandals.jpg', 'Inc.5'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Block Heel Sandals');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 4, 'Formal Oxford Shoes', 'Polished oxfords for office and occasion wear.', 2499.00, 'Tan', 'footwear/formal-oxfords.jpg', 'Red Tape'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Formal Oxford Shoes');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 5, 'Aviator Sunglasses', 'Classic metal aviators with UV-protective lenses.', 999.00, 'Black', 'accessories/aviator-sunglasses.jpg', 'Fastrack'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Aviator Sunglasses');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 5, 'Slim Leather Belt', 'Reversible slim belt with a brushed metal buckle.', 799.00, 'Brown', 'accessories/slim-belt.jpg', 'Allen Solly'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Slim Leather Belt');

INSERT INTO products (category_id, product_name, description, price, color, image, brand)
SELECT 5, 'Cotton Blockprint Scarf', 'Lightweight cotton scarf with a soft blockprint.', 699.00, 'Pink', 'accessories/cotton-scarf.jpg', 'Fabindia'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE product_name = 'Cotton Blockprint Scarf');

-- Clothing sizes
INSERT INTO product_sizes (product_id, size, stock)
SELECT p.product_id, s.size, s.stock
FROM products p
JOIN (
    SELECT 'S' AS size, 10 AS stock UNION ALL
    SELECT 'M', 14 UNION ALL
    SELECT 'L', 11 UNION ALL
    SELECT 'XL', 7
) s
WHERE p.product_name IN (
    'Linen Oxford Shirt', 'Graphic Crew T-Shirt', 'Quilted Bomber Jacket',
    'Pique Polo T-Shirt', 'Knit Crop Top', 'A-Line Midi Dress',
    'Tailored Blazer', 'Printed Tunic Top', 'Kids Colorblock Hoodie',
    'Wide Leg Trousers'
)
AND NOT EXISTS (
    SELECT 1 FROM product_sizes ps
    WHERE ps.product_id = p.product_id AND ps.size = s.size
);

INSERT INTO product_sizes (product_id, size, stock)
SELECT p.product_id, s.size, s.stock
FROM products p
JOIN (
    SELECT '30' AS size, 8 AS stock UNION ALL
    SELECT '32', 12 UNION ALL
    SELECT '34', 9 UNION ALL
    SELECT '36', 6
) s
WHERE p.product_name IN ('Slim Chino Trousers', 'Utility Cargo Trousers')
AND NOT EXISTS (
    SELECT 1 FROM product_sizes ps
    WHERE ps.product_id = p.product_id AND ps.size = s.size
);

INSERT INTO product_sizes (product_id, size, stock)
SELECT p.product_id, s.size, s.stock
FROM products p
JOIN (
    SELECT '28' AS size, 7 AS stock UNION ALL
    SELECT '30', 11 UNION ALL
    SELECT '32', 9 UNION ALL
    SELECT '34', 5
) s
WHERE p.product_name = 'High-Rise Skinny Jeans'
AND NOT EXISTS (
    SELECT 1 FROM product_sizes ps
    WHERE ps.product_id = p.product_id AND ps.size = s.size
);

INSERT INTO product_sizes (product_id, size, stock)
SELECT p.product_id, s.size, s.stock
FROM products p
JOIN (
    SELECT '6' AS size, 6 AS stock UNION ALL
    SELECT '7', 8 UNION ALL
    SELECT '8', 10 UNION ALL
    SELECT '9', 7
) s
WHERE p.product_name IN (
    'Kids Court Sneakers', 'Leather Ankle Boots',
    'Block Heel Sandals', 'Formal Oxford Shoes'
)
AND NOT EXISTS (
    SELECT 1 FROM product_sizes ps
    WHERE ps.product_id = p.product_id AND ps.size = s.size
);

INSERT INTO product_sizes (product_id, size, stock)
SELECT p.product_id, 'One Size', 20
FROM products p
WHERE p.product_name IN ('Aviator Sunglasses', 'Slim Leather Belt', 'Cotton Blockprint Scarf')
AND NOT EXISTS (
    SELECT 1 FROM product_sizes ps
    WHERE ps.product_id = p.product_id AND ps.size = 'One Size'
);
