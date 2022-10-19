package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.exception.ProductDoesNotExist;
import io.github.paparadva.myfooddiary.mapper.ConsumedProductMapper;
import io.github.paparadva.myfooddiary.repository.ConsumedProductRepository;
import io.github.paparadva.myfooddiary.repository.ProductRepository;
import io.github.paparadva.myfooddiary.web.dto.ConsumedProductRequest;
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
    public void saveConsumedProducts(LocalDate date, List<ConsumedProductRequest> productRequests) {
        consumedProductRepository.deleteAllByConsumptionDate(date);

        var consumedProducts = IntStream
                .range(0, productRequests.size())
                .peek(index -> {
                    var request = productRequests.get(index);
                    if (!productRepository.existsById(request.productName())) {
                        throw new ProductDoesNotExist(request.productName());
                    }
                })
                .mapToObj(index -> {
                    var request = productRequests.get(index);
                    var consumedProduct = mapper.consumedProductRequestToEntity(request, date, index);
                    log.info("Mapped ConsumedProduct: {}", consumedProduct);
                    return consumedProduct;
                })
                .toList();

        consumedProductRepository.saveAll(consumedProducts);
    }
}
