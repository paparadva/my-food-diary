CREATE SCHEMA my_food_diary;

CREATE TABLE my_food_diary.product (
    name VARCHAR(100) NOT NULL,
    kcal INTEGER NOT NULL,
    protein FLOAT NOT NULL,
    fat FLOAT NOT NULL,
    carb FLOAT NOT NULL,
    CONSTRAINT product_id PRIMARY KEY (name)
);

comment on table my_food_diary.product is 'Пищевая ценность продукта (на 100г)';
comment on column my_food_diary.product.name is 'Название продукта';
comment on column my_food_diary.product.kcal is 'ккал';
comment on column my_food_diary.product.fat is 'жиры';
comment on column my_food_diary.product.protein is 'белки';
comment on column my_food_diary.product.carb is 'углеводы';