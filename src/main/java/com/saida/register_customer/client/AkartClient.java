package com.saida.register_customer.client;

import com.saida.register_customer.dto.request.UserRequestDto;
import com.saida.register_customer.dto.response.TokenDto;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.jackson.JacksonDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "akart", url = "${services.akart.url}",
        configuration = AkartClient.FeignClientConfiguration.class)
public interface AkartClient {

    @PostMapping("/createToken")
    TokenDto getToken(@RequestBody UserRequestDto userRequestDto);

    @RequiredArgsConstructor
    class FeignClientConfiguration {

        private final ApplicationProperties applicationProperties;

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
            return new BasicAuthRequestInterceptor(applicationProperties.getUsername(),
                    applicationProperties.getPassword());
        }

        @Bean
        public ErrorDecoder feignErrorDecoder() {
            return AnnotationErrorDecoder
                    .builderFor(AkartClient.class)
                    .withResponseBodyDecoder(new JacksonDecoder())
                    .build();
        }
    }
}
