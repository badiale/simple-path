package br.com.badiale.simplepath;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * Configurações base da aplicação.
 */
@Configuration
@ComponentScan
public class ApplicationConfiguration {

    /**
     * Bean para fazer validação de dados antes de persistir na base.
     */
    @Bean
    public Validator beanValidation() {
        return new LocalValidatorFactoryBean();
    }
}
