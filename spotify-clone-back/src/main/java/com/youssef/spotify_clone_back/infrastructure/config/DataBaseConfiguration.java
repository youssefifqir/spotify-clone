package com.youssef.spotify_clone_back.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.youssef.spotify_clone_back.userContext.repository",
        "com.youssef.spotify_clone_back.catalogueContext.repository"})
@EnableTransactionManagement
@EnableJpaAuditing
public class DataBaseConfiguration {

}
