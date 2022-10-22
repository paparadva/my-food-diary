package io.github.paparadva.myfooddiary.mapper;

import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequestDto;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsumedProductMapper {

    ConsumedProduct requestDtoToEntity(ConsumedProductRequestDto requestDto,
                                       LocalDate consumptionDate,
                                       int entryIndex);

    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = ".", source = "product")
    ConsumedProductResponseDto entityToResponseDto(ConsumedProduct consumedProduct);
}
