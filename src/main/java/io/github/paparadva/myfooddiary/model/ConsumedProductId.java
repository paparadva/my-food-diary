package io.github.paparadva.myfooddiary.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ConsumedProductId implements Serializable {
    private LocalDate consumptionDate;
    private Integer entryIndex;
}
