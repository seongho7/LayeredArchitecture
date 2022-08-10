package com.sh.membership.integration

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.membership.common.response.CommonResponse
import com.sh.membership.point.presentation.rest.CollectPointReq
import com.sh.membership.point.presentation.rest.PointApiUrl
import com.sh.membership.point.presentation.rest.UsePointReq
import com.sh.membership.point.presentation.rest.UsePointRes
import com.sh.membership.pointhistory.presentation.rest.HistoryList
import com.sh.membership.pointhistory.presentation.rest.PointHistoryApiUrl
import com.sh.membership.common.ExistBarcode
import com.sh.membership.common.ExistStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

class PointIntegrationTest : AbstractIntegrationTest() {
    @DisplayName("포인트를 등록, 사용하고 내역을 조회합니다.")
    @Test
    fun collect_point_use_point_correctly() {
        val collectPointReq = CollectPointReq(storeId = ExistStore.A.id.id, barcode = ExistBarcode.barcode2, pointAmount = 10)

        val collectAction = mockMvc.perform(
            MockMvcRequestBuilders.post(PointApiUrl.COLLECT_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(collectPointReq))
        )
        collectAction.andDo(MockMvcResultHandlers.print())


        val usePointReq = UsePointReq(storeId = collectPointReq.storeId, barcode = collectPointReq.barcode, pointAmount = collectPointReq.pointAmount)

        val useAction = mockMvc.perform(
            MockMvcRequestBuilders.put(PointApiUrl.USE_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usePointReq))
        )
        useAction.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<UsePointRes>>(){})
                assertEquals(CommonResponse.Result.SUCCESS, res.result)
                assertEquals(0, res.data?.currentPoint)
            }


        val historyAction = mockMvc.perform(
            MockMvcRequestBuilders.get(PointHistoryApiUrl.SEARCH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("barcode", collectPointReq.barcode)
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString())
                .param("page", "0")
                .param("pageSize", "10")
        )
        historyAction.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<HistoryList>>(){})
                assertEquals(CommonResponse.Result.SUCCESS, res.result)
                assertTrue(res.data!!.history.size == 2)
                assertEquals(10, res.data!!.history[0].currentPoint)
                assertEquals(10, res.data!!.history[0].changedPoint)
                assertEquals(0, res.data!!.history[1].currentPoint)
                assertEquals(10, res.data!!.history[1].changedPoint)
            }
    }
}