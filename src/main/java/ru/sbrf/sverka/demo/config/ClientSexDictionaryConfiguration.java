package ru.sbrf.sverka.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Из настроечного файла application.yml достает
 * пол каждого клиента и записывает clientId'шники
 * в списки males / females
 */
@Configuration
public class ClientSexDictionaryConfiguration {

    private final List<String> males;
    private final List<String> females;

    public ClientSexDictionaryConfiguration(
            @Value("${clients.males}") List<String> males,
            @Value("${clients.females}") List<String> females
    ) {
        this.males = males;
        this.females = females;
    }

    @Bean
    public List<String> males() {
        return males;
    }

    @Bean
    public List<String> females() {
        return females;
    }
}
