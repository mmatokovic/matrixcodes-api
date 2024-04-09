package dev.mmatokovic.matrixcodesapi

import dev.mmatokovic.matrixcodesapi.matrixcode.MatrixcodeHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    @FlowPreview
    @Bean
    fun mainRouter(handler: MatrixcodeHandler) = coRouter {
        "/v1".nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/matrixcodes", handler::listMatrixcodes)
                POST("/matrixcodes", handler::createMatrixcode)
                GET("/matrixcodes/{id}", handler::findMatrixcodeById)
                PATCH("/matrixcodes/{id}", handler::updateMatrixcode)
                DELETE("/matrixcodes/{id}", handler::deleteMatrixcode)
            }
        }
    }
}