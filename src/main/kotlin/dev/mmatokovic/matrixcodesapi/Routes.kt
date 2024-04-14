package dev.mmatokovic.matrixcodesapi

import dev.mmatokovic.matrixcodesapi.matrixcodes.MatrixcodeHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class Routes {

    @FlowPreview
    @Bean
    fun router(handler: MatrixcodeHandler) = coRouter {
        "/v1".nest{
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/matrixcodes", handler::listMatrixcodes)
                POST("/matrixcodes", handler::createMatrixcode)
                GET("/matrixcodes/{id}", handler::findMatrixcodeById)
                PATCH("/matrixcodes/{id}", handler::updateMatrixcode)
                DELETE("/matrixcodes/{id}", handler::deleteMatrixcode)
                GET("/matrixcodes/openapi", handler::getResourceFileAsString)
            }
        }
    }
}