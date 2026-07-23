package com.developer.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

	@Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        modelMapper.getConfiguration()
            .setAmbiguityIgnored(true) // Ignora ambiguidades de nomes iguais
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            // 💡 ESTA LINHA IMPEDE O MODELMAPPER DE CONVERTER COLOÇÕES AUTOMATICAMENTE
            .setDeepCopyEnabled(false); 

        return modelMapper;
    }
}