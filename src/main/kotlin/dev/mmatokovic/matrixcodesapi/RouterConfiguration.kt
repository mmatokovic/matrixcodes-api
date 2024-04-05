package dev.mmatokovic.matrixcodesapi

import dev.mmatokovic.matrixcodesapi.matrixcode.MatrixcodeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
    @Bean
    fun mainRouter(matrixcodeHandler: MatrixcodeHandler) = coRouter {
        GET("/matrixcode", matrixcodeHandler::listMatrixcodes)
        GET("/matrixcode/{id}", accept(APPLICATION_JSON), matrixcodeHandler::findMatrixcodeById)
    }
}