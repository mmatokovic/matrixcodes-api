package dev.mmatokovic.matrixcodesapi

import dev.mmatokovic.matrixcodesapi.matrixcode.MatrixcodeHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    @FlowPreview
    @Bean
    fun mainRouter(matrixcodeHandler: MatrixcodeHandler) = coRouter {
        "/v1".nest {
            GET("/matrixcode", matrixcodeHandler::listMatrixcodes)
            POST("/matrixcode", matrixcodeHandler::createMatrixcode)
            GET("/matrixcode/{id}", accept(APPLICATION_JSON), matrixcodeHandler::findMatrixcodeById)
        }
    }
}