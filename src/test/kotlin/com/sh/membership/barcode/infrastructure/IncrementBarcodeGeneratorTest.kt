package com.sh.membership.barcode.infrastructure

import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.Executors


@ActiveProfiles("test")
@Import(IncrementBarcodeGenerator::class)
@SpringBootTest
class IncrementBarcodeGeneratorTest {


    @Autowired
    private lateinit var incrementBarcodeGenerator: IncrementBarcodeGenerator

    @DisplayName("멀티쓰레드 요청시 각기 다른 바코드가 생성이 된다.")
    @Test
    fun generate_multi_thread_request() {
        val executionCount = 5
        val scope = CoroutineScope(Executors.newFixedThreadPool(executionCount).asCoroutineDispatcher())

        val command = IssueBarcodeCommand(10)
        val barcodeSet = HashSet<String>()
        scope.launch {
            1.rangeTo(executionCount).map {
                launch {
                    val barcode = incrementBarcodeGenerator.generate(command)
                    println("barcode:${barcode.barcode}")
                    barcodeSet.add(barcode.barcode)
                }
            }
        }
        Thread.sleep(500)
        assertEquals(5, barcodeSet.size)
    }

    @DisplayName("멤버십 바코드는 10자리 숫자형 스트링으로 생성이 된다.")
    @Test
    fun generate_success() {
        val command = IssueBarcodeCommand(10)
        val barcode = incrementBarcodeGenerator.generate(command)
        assertTrue(barcode.barcode.toLong() > BarcodeGenerator.MIN_VALUE)
        assertTrue(barcode.barcode.toLong() < BarcodeGenerator.MAX_VALUE)
    }
}