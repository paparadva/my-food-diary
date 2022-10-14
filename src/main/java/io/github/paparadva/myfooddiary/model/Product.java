package io.github.paparadva.myfooddiary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product", schema = "my_food_diary")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String name;
    private int kcal;
    private double protein;
    private double fat;
    private double carb;
}
