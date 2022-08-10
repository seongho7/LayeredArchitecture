package com.sh.membership.point.domain.use

import com.sh.membership.barcode.domain.validate.BarcodeNotFoundException
import com.sh.membership.point.domain.StoreNotFoundException
import com.sh.membership.point.domain.collect.CollectPointCommand
import com.sh.membership.point.domain.collect.CollectPointService
import com.sh.membership.store.domain.StoreId
import com.sh.membership.common.ExistBarcode
import com.sh.membership.common.ExistStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@DisplayName("포인트 적립 유스케이스")
@ActiveProfiles("test")
@SpringBootTest
class UsePointServiceTest {
    @Autowired
    private lateinit var userPointService: UsePointService

    @Autowired
    private lateinit var collectPointService: CollectPointService

    @DisplayName("등록되지 않은 상점 id 경우 등록되지 않은 상점 오류를 돌려줍니다.")
    @Test
    fun use_point_store_not_found() {
        val command = UsePointCommand(StoreId(99999999999L), ExistBarcode.barcode1, 1L)
        assertThrows(StoreNotFoundException::class.java) {
            userPointService.usePoint(command)
        }
    }

    @DisplayName("등록되지 않은 멤버십 바코드의 경우 등록되지 않은 멤버십 바코드 오류를 돌려줍니다.")
    @Test
    fun use_point_barcode_not_found() {
        val command = UsePointCommand(ExistStore.B.id, "barcode", 1L)
        assertThrows(BarcodeNotFoundException::class.java) {
            userPointService.usePoint(command)
        }
    }

    @Transactional
    @DisplayName("B업종 정립금이 있더라도 요청 상점이 A업종이라면 사용할수 없습니다")
    @Test
    fun use_point_not_enough() {
        val collectPointCommand = CollectPointCommand(ExistStore.C.id, ExistBarcode.barcode2, 10L)
        collectPointService.collectPoint(collectPointCommand)

        val usePointCommand = UsePointCommand(ExistStore.B.id, ExistBarcode.barcode2, 1L)
        assertThrows(PointNotEnoughException::class.java) {
            userPointService.usePoint(usePointCommand)
        }
    }

    @Transactional
    @DisplayName("같은업종의 적립금이 있으면 사용할수 있습니다")
    @Test
    fun use_point_correctly() {
        val collectPointCommand = CollectPointCommand(ExistStore.C.id, ExistBarcode.barcode2, 10L)
        collectPointService.collectPoint(collectPointCommand)

        val usePointCommand = UsePointCommand(ExistStore.C.id, ExistBarcode.barcode2, 1L)
        val point = userPointService.usePoint(usePointCommand)
        assertEquals(9, point.point)

    }
}