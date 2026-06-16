package com.example.simpledrone.config;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;
    private final String databaseType;

    public DatabaseInitializer(DataSource dataSource,
                               ResourceLoader resourceLoader,
                               @Value("${app.database.type:sqlite}") String databaseType) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.resourceLoader = resourceLoader;
        this.databaseType = databaseType;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            Resource resource = resourceLoader.getResource("classpath:db/schema-" + databaseType + ".sql");
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            Arrays.stream(sql.split(";"))
                    .map(String::trim)
                    .filter(item -> !item.isEmpty())
                    .forEach(jdbcTemplate::execute);
        } catch (Exception ex) {
            throw new IllegalStateException("Database init failed", ex);
        }
    }
}
