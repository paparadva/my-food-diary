package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.exception.ProductDoesNotExist;
import io.github.paparadva.myfooddiary.mapper.ConsumedProductMapper;
import io.github.paparadva.myfooddiary.model.ConsumedProduct;
import io.github.paparadva.myfooddiary.repository.ConsumedProductRepository;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequestDto;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumedProductServiceImpl implements ConsumedProductService {

    private final ConsumedProductRepository consumedProductRepository;
    private final ProductRepository productRepository;
    private final ConsumedProductMapper mapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void saveConsumedProducts(LocalDate date, List<ConsumedProductRequestDto> productRequests) {
        consumedProductRepository.deleteAllByConsumptionDate(date);

        var consumedProducts = IntStream
                .range(0, productRequests.size())
                .mapToObj(index -> {
                    var request = productRequests.get(index);

                    var product = productRepository.findById(request.productName())
                            .orElseThrow(() -> new ProductDoesNotExist(request.productName()));

                    var consumedProduct = mapper.requestDtoToEntity(request, date, index);
                    consumedProduct.setProduct(product);

                    log.info("Mapped ConsumedProduct: {}", consumedProduct);
                    return consumedProduct;
                })
                .toList();

        consumedProductRepository.saveAll(consumedProducts);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public ConsumedProductsResponse getConsumedProducts(LocalDate date) {
        List<ConsumedProduct> consumedProducts = consumedProductRepository.getAllByConsumptionDateOrderByEntryIndexAsc(date);
        log.info("Found consumed products for date={}: {}", date, consumedProducts);
        return new ConsumedProductsResponse(date, consumedProducts
                .stream()
                .map(mapper::entityToResponseDto)
                .toList());
    }
}
