package io.github.paparadva.myfooddiary.repository;

import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.model.ConsumedProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsumedProductRepository extends JpaRepository<ConsumedProduct, ConsumedProductId> {

    @Query("SELECT c FROM ConsumedProduct c JOIN FETCH c.product " +
            "WHERE c.consumptionDate = :consumptionDate ORDER BY c.entryIndex ASC")
    List<ConsumedProduct> getAllByConsumptionDateOrderByEntryIndexAsc(LocalDate consumptionDate);

    void deleteAllByConsumptionDate(LocalDate consumptionDate);
}
