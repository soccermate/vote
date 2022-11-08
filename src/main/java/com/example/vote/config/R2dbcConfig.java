package com.example.vote.config;

import com.example.vote.repository.converter.VoteDetailReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement
public class R2dbcConfig extends AbstractR2dbcConfiguration {
    ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    ReactiveTransactionManager r2dbcTransactionManager(ConnectionFactory connectionFactory)
    {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return null;
    }

    @Bean
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new VoteDetailReader(objectMapper));
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }


}