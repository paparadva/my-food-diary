package io.github.paparadva.myfooddiary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "product")
@AllArgsConstructor
@Data
public class ProductSuggestion {
    @Id
    private String name;
}
