package com.sh.membership.integration

import com.fasterxml.jackson.core.type.TypeReference
import com.sh.membership.common.response.CommonResponse
import com.sh.membership.common.response.ErrorCode
import com.sh.membership.pointhistory.presentation.rest.HistoryList
import com.sh.membership.pointhistory.presentation.rest.PointHistoryApiUrl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

class PointHistoryIntegrationTest : AbstractIntegrationTest() {
    @DisplayName("등록되지 않은 멤버십 바코드의 경우 등록되지 않은 멤버십 바코드 오류를 돌려줍니다.")
    @Test
    fun point_history_barcode_not_found() {
        val historyAction = mockMvc.perform(
            MockMvcRequestBuilders.get(PointHistoryApiUrl.SEARCH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .param("barcode", "99999999999999")
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().toString())
                .param("page", "0")
                .param("pageSize", "10")
        )
        historyAction.andDo(MockMvcResultHandlers.print())
            .andExpect { MockMvcResultMatchers.status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object: TypeReference<CommonResponse<HistoryList>>(){})
                assertEquals(CommonResponse.Result.FAIL, res.result)
                assertEquals(ErrorCode.BARCODE_NOT_FOUND.name, res.errorCode)
            }
    }
}