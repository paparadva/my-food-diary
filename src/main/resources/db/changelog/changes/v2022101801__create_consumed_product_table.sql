
CREATE TABLE my_food_diary.consumed_product (
    consumption_date DATE NOT NULL,
    entry_index INTEGER NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    grams INTEGER NOT NULL,
    CONSTRAINT consumed_product_id PRIMARY KEY (consumption_date, entry_index),
    CONSTRAINT consumed_product_name_fk FOREIGN KEY (product_name) REFERENCES my_food_diary.product(name)
);

comment on table my_food_diary.consumed_product is 'История расчетов';
comment on column my_food_diary.consumed_product.consumption_date is 'Дата расчета';
comment on column my_food_diary.consumed_product.product_name is 'Название продукта';
comment on column my_food_diary.consumed_product.grams is 'Масса продукта';
comment on column my_food_diary.consumed_product.entry_index is 'Номер строки в таблице для расчета';
