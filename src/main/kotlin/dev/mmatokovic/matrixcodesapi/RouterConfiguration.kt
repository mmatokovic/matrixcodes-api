package dev.mmatokovic.matrixcodesapi

import dev.mmatokovic.matrixcodesapi.matrixcode.MatrixcodeHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    @FlowPreview
    @Bean
    fun mainRouter(matrixcodeHandler: MatrixcodeHandler) = coRouter {
        "/v1".nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/matrixcode", matrixcodeHandler::listMatrixcodes)
                POST("/matrixcode", matrixcodeHandler::createMatrixcode)
                GET("/matrixcode/{id}", matrixcodeHandler::findMatrixcodeById)
                PATCH("/matrixcode/{id}", matrixcodeHandler::updateMatrixcode)
                DELETE("/matrixcode/{id}", matrixcodeHandler::deleteMatrixcode)
            }
        }
    }
}