package com.sh.membership.barcode.presentation.rest

import com.sh.membership.barcode.application.BarcodeFacade
import com.sh.membership.barcode.domain.Barcode
import com.sh.membership.barcode.domain.issue.IssueBarcodeCommand
import com.sh.membership.common.response.CommonResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.Min


@RequestMapping("${BarcodeApiUrl.ISSUE}")
@RestController
class IssueBarcodeController(
    private val barcodeFacade: BarcodeFacade
) {
    @PostMapping
    fun issueBarcode(@RequestBody @Valid req: IssueBarcodeReq) : CommonResponse<IssueBarcodeRes> {
        val barcode = barcodeFacade.issueBarcode(req.toCommand())
        return CommonResponse.success(IssueBarcodeRes(barcode))
    }
}

data class IssueBarcodeReq(
    @field:Min(100_000_000)
    val userId:Int
) {
    fun toCommand() = IssueBarcodeCommand(userId)
}

data class IssueBarcodeRes(
    val userId:Int,
    val barcode:String
) {
    constructor(barcode:Barcode) : this(userId = barcode.userId, barcode = barcode.barcode)
}