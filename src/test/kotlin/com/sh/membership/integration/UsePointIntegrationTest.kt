package com.sh.membership.integration

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.membership.common.response.CommonResponse
import com.sh.membership.common.response.ErrorCode
import com.sh.membership.point.presentation.rest.PointApiUrl
import com.sh.membership.point.presentation.rest.UsePointReq
import com.sh.membership.point.presentation.rest.UsePointRes
import com.sh.membership.common.ExistBarcode
import com.sh.membership.common.ExistStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class UsePointIntegrationTest : AbstractIntegrationTest() {


    @DisplayName("포인트 사용요청시 적립 금액을 초과하는 사용의 경우 포인트 부족으로 사용할수 없다는 오류를 돌려줍니다")
    @Test
    fun use_point_not_enough() {
        val req = UsePointReq(storeId = ExistStore.A.id.id, barcode = ExistBarcode.barcode1, pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.put(PointApiUrl.USE_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<UsePointRes>>(){})
                assertEquals(ErrorCode.POINT_NOT_ENOUGH.name, res.errorCode)
            }
    }

    @DisplayName("등록되지 않은 상점 id 경우 등록되지 않은 상점 오류를 돌려줍니다.")
    @Test
    fun use_point_store_not_found() {
        val req = UsePointReq(storeId = 99999999999, barcode = ExistBarcode.barcode1, pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.put(PointApiUrl.USE_POINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<UsePointRes>>(){})
                assertEquals(ErrorCode.STORE_NOT_FOUND.name, res.errorCode)
            }
    }

    @DisplayName("등록되지 않은 멤버십 바코드의 경우 등록되지 않은 멤버십 바코드 오류를 돌려줍니다.")
    @Test
    fun use_point_barcode_not_found() {
        val req = UsePointReq(storeId = ExistStore.A.id.id, barcode = "9999999999", pointAmount = 10)

        val action = mockMvc.perform(
            MockMvcRequestBuilders.put(PointApiUrl.USE_POINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<UsePointRes>>(){})
                assertEquals(ErrorCode.BARCODE_NOT_FOUND.name, res.errorCode)
            }
    }
}