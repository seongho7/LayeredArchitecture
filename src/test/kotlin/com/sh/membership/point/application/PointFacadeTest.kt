package com.sh.membership.point.application

import com.sh.membership.point.domain.collect.CollectPointCommand
import com.sh.membership.point.domain.use.UsePointCommand
import com.sh.membership.common.ExistBarcode
import com.sh.membership.common.ExistStore
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.Executors

@ActiveProfiles("test")
@SpringBootTest
class PointFacadeTest {
    @Autowired
    private lateinit var pointFacade: PointFacade


    @Transactional
    @DisplayName("포인트 등록 요청이 동시에 들어옵니다.")
    @Test
    fun collect_point_multi_thread() {
        val executionCount = 2

        val scope = CoroutineScope(Executors.newFixedThreadPool(executionCount).asCoroutineDispatcher())

        val command = CollectPointCommand(ExistStore.A.id, ExistBarcode.barcode1, 1L)
        val pointList = Collections.synchronizedList(mutableListOf<Long>())
        scope.launch {
            1.rangeTo(executionCount).map {
                launch {
                    val point = pointFacade.collectPoint(command)
                    pointList.add(point.point)
                }
            }
        }
        Thread.sleep(3000)
        assertEquals(2, pointList.size)
        assertEquals(1, pointList[0])
        assertEquals(2, pointList[1])
    }

    @DisplayName("포인트 사용 요청이 동시에 들어옵니다.")
    @Test
    fun use_point_multi_thread() {
        val collectPointCommand = CollectPointCommand(ExistStore.A.id, ExistBarcode.barcode1, 2L)
        pointFacade.collectPoint(collectPointCommand)

        val executionCount = 2
        val scope = CoroutineScope(Executors.newFixedThreadPool(executionCount).asCoroutineDispatcher())

        val pointList = Collections.synchronizedList(mutableListOf<Long>())
        val command = UsePointCommand(ExistStore.A.id, ExistBarcode.barcode1, 1L)
        scope.launch {
            1.rangeTo(executionCount).map {
                launch {
                    val point = pointFacade.usePoint(command)
                    pointList.add(point.point)
                }
            }
        }
        Thread.sleep(3000)
        assertEquals(2, pointList.size)
        assertEquals(1, pointList[0])
        assertEquals(0, pointList[1])
    }
}