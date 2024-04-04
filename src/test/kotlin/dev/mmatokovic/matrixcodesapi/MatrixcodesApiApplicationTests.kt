package dev.mmatokovic.matrixcodesapi

import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MatrixcodesApiApplicationTests (
		@Autowired val matrixcodeRepository: MatrixcodeRepository){

	@Test
	fun contextLoads() {
		runBlocking {
			matrixcodeRepository.save (Matrixcode(null, "Example", null, null, null, null, null, null,))
			val matrixcodes = matrixcodeRepository.findAll()

			Assertions.assertNotNull(matrixcodes.last().id)
			Assertions.assertEquals(matrixcodes.count(), 3)
		}
	}
}
