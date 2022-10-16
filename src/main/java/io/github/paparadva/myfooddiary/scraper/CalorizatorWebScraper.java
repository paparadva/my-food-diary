package io.github.paparadva.myfooddiary.scraper;

import io.github.paparadva.myfooddiary.config.ScrapingConfig;
import io.github.paparadva.myfooddiary.model.Product;
import io.github.paparadva.myfooddiary.model.ProductSuggestion;
import io.github.paparadva.myfooddiary.service.ProductService;
import io.github.paparadva.myfooddiary.service.ProductSuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class CalorizatorWebScraper {

    private final ScrapingConfig config;
    private final ProductSuggestionService suggestionService;
    private final ProductService productService;

    @Async
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        try {
            scrapeCalorizator();
        } catch (InterruptedException e) {
            log.info("Scraping thread was interrupted");
        } catch (Exception e) {
            log.error("Scraping failed with exception", e);
        }
    }

    private void scrapeCalorizator() throws IOException, InterruptedException {
        log.info("Scraping started");

        int pageCount = Math.min(findTotalPageCount(), config.getLoadPageLimit());
        Thread.sleep(config.getPauseTimeMs());

        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            List<Product> products = findProductsOnPage(pageIndex);
            productService.saveProducts(products);
            suggestionService.saveProducts(products.stream()
                    .map(product -> new ProductSuggestion(product.getName()))
                    .toList());
            Thread.sleep(config.getPauseTimeMs());
        }

        log.info("Scraping finished");
    }


    private int findTotalPageCount() throws IOException {
        log.info("Parsing page for page count");
        Document doc = Jsoup.connect(config.getCalorizatorUrl() + "0").get();

        var li = doc.selectFirst(".pager-last");
        Objects.requireNonNull(li, ".pager-last element not found");

        var a = li.firstElementChild();
        Objects.requireNonNull(a, ".pager-last has no children");

        String aText = a.text();

        int pageCount = parseInt(aText, "link text inside .pager-last is not a number");
        log.info("Found page count: " + pageCount);
        return pageCount;
    }

    private List<Product> findProductsOnPage(int pageIndex) throws IOException {
        log.info("Scraping page index: " + pageIndex);
        Document doc = Jsoup.connect(config.getCalorizatorUrl() + pageIndex).get();

        Elements productRows = doc.select(".views-table tbody tr");
        log.info("Found {} products on this page", productRows.size());

        return productRows.stream()
                .map(trElement -> {
                    if (trElement.childrenSize() != 6) {
                        throw new RuntimeException("Table row is expected to have 6 children, found " + trElement.childrenSize());
                    }

                    var nameColumn = trElement.child(1);
                    Objects.requireNonNull(nameColumn, "Product name column not found");
                    var productName = Objects.requireNonNull(
                            nameColumn.firstElementChild(),
                            "name column has no children")
                            .text();

                    double protein = parseDouble(trElement.child(2).text(), "invalid protein value");

                    double fat = parseDouble(trElement.child(3).text(), "invalid fat value");

                    double carb = parseDouble(trElement.child(4).text(), "invalid carbs value");

                    int kcal = parseInt(trElement.child(5).text(), "invalid kcal value");

                    return new Product(productName, kcal, protein, fat, carb);
                })
                .toList();
    }

    private static double parseDouble(String text, String errorMessage) {
        try {
            return text.isBlank() ? 0 : Double.parseDouble(text);
        } catch (NumberFormatException e) {
            log.error(errorMessage, e);
            throw e;
        }
    }

    private static int parseInt(String text, String errorMessage) {
        try {
            return text.isBlank() ? 0 : Integer.parseInt(text);
        } catch (NumberFormatException e) {
            log.error(errorMessage, e);
            throw e;
        }
    }
}
