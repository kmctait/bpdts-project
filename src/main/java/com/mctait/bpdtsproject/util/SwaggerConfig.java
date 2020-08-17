package com.mctait.bpdtsproject.util;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors
			.basePackage("com.mctait.bpdtsproject"))
			.paths(regex("/users.*"))
			.build()
			.apiInfo(metaData());
	}

	private ApiInfo metaData() {
		ApiInfo apiInfo = new ApiInfo("BPDTS Project API Reference",
			"This API was written in response to the interview process for DWP",
			"1.0",
			"",
			new Contact("Kevin McTait", "https://www.github.com/kmctait/bpdts-project", "kevin.mctait@gmail.com"),
			"", 
			"");
		return apiInfo;
	}
}
