package com.sh.membership.integration

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.membership.common.response.CommonResponse
import com.sh.membership.common.response.ErrorCode
import com.sh.membership.point.presentation.rest.CollectPointReq
import com.sh.membership.point.presentation.rest.CollectPointRes
import com.sh.membership.point.presentation.rest.PointApiUrl
import com.sh.membership.common.ExistBarcode
import com.sh.membership.common.ExistStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

class CollectPointIntegrationTest : AbstractIntegrationTest() {

    @Transactional
    @DisplayName("포인트를 적립합니다.")
    @Test
    fun collect_point_correctly() {
        val req = CollectPointReq(storeId = ExistStore.A.id.id, barcode = ExistBarcode.barcode1, pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.post(PointApiUrl.COLLECT_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<CollectPointRes>>(){})
                assertEquals(CommonResponse.Result.SUCCESS, res.result)
                assertEquals(req.pointAmount, res.data?.currentPoint)
            }
    }

    @DisplayName("등록되지 않은 상점 id 경우 등록되지 않은 상점 오류를 돌려줍니다.")
    @Test
    fun collect_point_store_not_found() {
        val req = CollectPointReq(storeId = 99999999999, barcode = ExistBarcode.barcode1, pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.post(PointApiUrl.COLLECT_POINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<CollectPointRes>>(){})
                assertEquals(ErrorCode.STORE_NOT_FOUND.name, res.errorCode)
            }
    }

    @DisplayName("등록되지 않은 멤버십 바코드의 경우 등록되지 않은 멤버십 바코드 오류를 돌려줍니다.")
    @Test
    fun collect_point_barcode_not_found() {
        val req = CollectPointReq(storeId = ExistStore.A.id.id, barcode = "9999999999", pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.post(PointApiUrl.COLLECT_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<CollectPointRes>>(){})
                assertEquals(ErrorCode.BARCODE_NOT_FOUND.name, res.errorCode)
            }
    }
}