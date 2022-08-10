package com.sh.membership.integration

import com.sh.membership.barcode.domain.issue.BarcodeGenerator
import com.sh.membership.barcode.presentation.rest.BarcodeApiUrl
import com.sh.membership.barcode.presentation.rest.IssueBarcodeRes
import com.sh.membership.barcode.presentation.rest.IssueBarcodeReq
import com.sh.membership.common.response.CommonResponse
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.fasterxml.jackson.core.type.TypeReference
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

class BarcodeIntegrationTest : AbstractIntegrationTest() {


    @DisplayName("멤버십 바코드는 10자리 숫자형 스트링으로 생성이 된다.")
    @Test
    fun issue_barcode_correctly() {
        val req = IssueBarcodeReq(1_000_000_000)

        val action = mockMvc.perform(post(BarcodeApiUrl.ISSUE)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(req))
        )
        action.andDo(print())
            .andExpect { status().isOk }
            .andExpect {
                val res = objectMapper.readValue(it.response.contentAsString, object:TypeReference<CommonResponse<IssueBarcodeRes>>(){})
                assertNotNull(res.data)
                assertTrue(res.data!!.barcode.toLong() > BarcodeGenerator.MIN_VALUE)
                assertTrue(res.data!!.barcode.toLong() < BarcodeGenerator.MAX_VALUE)
            }
    }
}