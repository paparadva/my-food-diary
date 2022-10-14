package io.github.paparadva.myfooddiary.service;

import io.github.paparadva.myfooddiary.model.ProductSuggestion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSuggestionServiceImpl implements ProductSuggestionService {

    private final static int RESULT_LIMIT = 10;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void saveProducts(List<ProductSuggestion> products) {
        log.info("Saving {} product documents", products.size());
        elasticsearchOperations.save(products);
    }

    @Override
    public List<String> fetchSuggestions(String query) {
        var wildcardQuery = QueryBuilders
                .wildcardQuery("name", "*" + query + "*")
                .caseInsensitive(true);
        log.info(wildcardQuery.toString());

        var matchQuery = QueryBuilders
                .matchPhrasePrefixQuery("name", query);
        log.info(matchQuery.toString());

        var searchQuery = new NativeSearchQueryBuilder()
                .withFilter(wildcardQuery)
                .withFilter(matchQuery)
                .withPageable(PageRequest.of(0, RESULT_LIMIT))
                .build();

        SearchHits<ProductSuggestion> suggestions = elasticsearchOperations.search(
                searchQuery,
                ProductSuggestion.class,
                IndexCoordinates.of("product"));
        log.info("Found {} product suggestions, limit is {}", suggestions.getTotalHits(), RESULT_LIMIT);

        return suggestions
                .stream()
                .map(suggestion -> suggestion.getContent().getName())
                .toList();
    }
}
