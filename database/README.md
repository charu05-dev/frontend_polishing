# FashionStore database files

These scripts recreate the existing MySQL schema and expand the product catalog. They do **not** change table structure.

## 1. Create the schema

```bash
mysql -u YOUR_USER -p < database/schema.sql
```

`schema.sql` creates the `fashion_store` database, tables, and the five categories already used by the app (Men, Women, Kids, Footwear, Accessories).

## 2. Load catalog images and products

Copy product images into:

```
src/main/webapp/images/products/
```

Then load the catalog:

```bash
mysql -u YOUR_USER -p fashion_store < database/seed-catalog.sql
```

The seed script:

- updates image paths for the original 12 products
- inserts additional fashion products only if the product name does not already exist
- adds matching `product_sizes` rows

It does not delete existing products, users, carts, or orders.

## 3. Application connection

Set local MySQL credentials in `src/main/java/com/fashionstore/util/DBConnection.java`. Do not commit production passwords.
