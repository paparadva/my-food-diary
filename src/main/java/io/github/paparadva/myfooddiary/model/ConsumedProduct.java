package io.github.paparadva.myfooddiary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@IdClass(ConsumedProductId.class)
@Table(name = "consumed_product", schema = "my_food_diary")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumedProduct {
    @Id
    private LocalDate consumptionDate;
    @Id
    private Integer entryIndex;
    private String productName;
    private Integer grams;
}
