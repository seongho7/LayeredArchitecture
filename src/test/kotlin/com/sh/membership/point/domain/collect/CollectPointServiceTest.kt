package com.sh.membership.point.domain.collect

import com.sh.membership.common.ExistBarcode
import com.sh.membership.barcode.domain.validate.BarcodeNotFoundException
import com.sh.membership.point.domain.StoreNotFoundException
import com.sh.membership.common.ExistStore
import com.sh.membership.point.domain.LoadPointPort
import com.sh.membership.store.domain.StoreId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@DisplayName("포인트 사용 유스케이스")
@ActiveProfiles("test")
@SpringBootTest
class CollectPointServiceTest{
    @Autowired
    private lateinit var collectPointService: CollectPointService
    @Autowired
    private lateinit var loadPointPort: LoadPointPort


    @DisplayName("등록되지 않은 상점 id 경우 등록되지 않은 상점 오류를 돌려줍니다.")
    @Test
    fun collect_point_store_not_found() {
        val command = CollectPointCommand(StoreId(99999999999L), ExistBarcode.barcode1, 1L)
        assertThrows(StoreNotFoundException::class.java) {
            collectPointService.collectPoint(command)
        }
    }

    @DisplayName("등록되지 않은 멤버십 바코드의 경우 등록되지 않은 멤버십 바코드 오류를 돌려줍니다.")
    @Test
    fun collect_point_barcode_not_found() {
        val command = CollectPointCommand(ExistStore.B.id, "barcode", 1L)
        assertThrows(BarcodeNotFoundException::class.java) {
            collectPointService.collectPoint(command)
        }
    }

    @DisplayName("포인트가 정상 등록이 됩니다.")
    @Transactional
    @Test
    fun collect_point_correctly() {
        val command = CollectPointCommand(ExistStore.B.id, ExistBarcode.barcode1, 1L)
        collectPointService.collectPoint(command)
        val point = loadPointPort.loadPoint(command.barcode, ExistStore.B.category)
        assertEquals(1L, point.get().point)
    }
}

