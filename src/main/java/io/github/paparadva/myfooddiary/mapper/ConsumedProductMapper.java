package io.github.paparadva.myfooddiary.mapper;

import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsumedProductMapper {

    ConsumedProduct consumedProductDtoToEntity(ConsumedProductDto consumedProductDto,
                                               LocalDate consumptionDate,
                                               int entryIndex);

    ConsumedProductDto consumedProductEntityToDto(ConsumedProduct consumedProduct);
}
