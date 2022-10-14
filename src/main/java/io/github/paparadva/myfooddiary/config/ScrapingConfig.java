package io.github.paparadva.myfooddiary.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "myfooddiary.scraping")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class ScrapingConfig {
    private final String calorizatorUrl;
    private final Boolean loadAllPages;
    private final Integer loadPageLimit;
    private final Integer pauseTimeMs;

    public Integer getLoadPageLimit() {
        return loadAllPages ? Integer.MAX_VALUE : loadPageLimit;
    }
}

