package io.github.paparadva.myfooddiary.repository;

import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.model.ConsumedProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ConsumedProductRepository extends JpaRepository<ConsumedProduct, ConsumedProductId> {

    void deleteAllByConsumptionDate(LocalDate consumptionDate);
}
