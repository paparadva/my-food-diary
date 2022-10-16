
ALTER TABLE my_food_diary.product ADD COLUMN ts_name tsvector
    GENERATED ALWAYS AS (to_tsvector('russian', name)) STORED;

comment on column my_food_diary.product.ts_name is 'Вектор по названию продукта для поиска';

CREATE INDEX product_ts_name_index ON my_food_diary.product USING GIN (ts_name);