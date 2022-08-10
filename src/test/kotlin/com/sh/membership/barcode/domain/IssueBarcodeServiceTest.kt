package com.sh.membership.barcode.domain

import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import com.sh.membership.barcode.domain.issue.IssueBarcodeService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.concurrent.Executors

@ActiveProfiles("test")
@SpringBootTest
class IssueBarcodeServiceTest {

    @Autowired
    private lateinit var issueBarcodeService: IssueBarcodeService


    @TestFactory
    @DisplayName("바코드 생성 유스케이스")
    fun issue_barcode_use_case() : Collection<DynamicTest> {
        val command = IssueBarcodeCommand(1)

        return listOf(
            DynamicTest.dynamicTest("멤버십 바코드는 10자리 숫자형 스트링을 사용하여 생성됩니다.") {
                val barcode = issueBarcodeService.issueBarcode(command)
                assertEquals(command.userId, barcode.userId)
                assertTrue(barcode.barcode.toLong() > BarcodeGenerator.MIN_VALUE)
                assertTrue(barcode.barcode.toLong() < BarcodeGenerator.MAX_VALUE)
            },

            DynamicTest.dynamicTest("이미 발급된 id의 발급 요청이 올 경우 기존 멤버십 바코드를 반환합니다.") {
                val barcode1 = issueBarcodeService.issueBarcode(command)
                val barcode2 = issueBarcodeService.issueBarcode(command)
                assertEquals(barcode1.barcode, barcode2.barcode)
            },

            DynamicTest.dynamicTest("멀티쓰레드 요청시 각기 다른 바코드가 생성이 된다.") {
                val executionCount = 5

                val scope = CoroutineScope(Executors.newFixedThreadPool(executionCount).asCoroutineDispatcher())

                val barcodeSet = HashSet<String>()
                scope.launch {
                    1.rangeTo(executionCount).map {
                        val command = IssueBarcodeCommand(it)
                        launch {
                            val barcode = issueBarcodeService.issueBarcode(command)
                            barcodeSet.add(barcode.barcode)
                        }
                    }
                }
                Thread.sleep(500)
                assertEquals(5, barcodeSet.size)
            },
        )
    }
}