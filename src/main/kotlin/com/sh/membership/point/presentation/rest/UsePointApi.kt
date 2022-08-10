package com.sh.membership.point.presentation.rest

import com.sh.membership.common.response.CommonResponse
import com.sh.membership.point.application.PointFacade
import com.sh.membership.point.domain.Point
import com.sh.membership.point.domain.use.UsePointCommand
import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RequestMapping("${PointApiUrl.USE_POINT}")
@RestController
class UsePointController(
    private val pointFacade: PointFacade
) {

    @PutMapping
    fun usePoint(@Valid @RequestBody req: UsePointReq) : CommonResponse<UsePointRes> {
        val point = pointFacade.usePoint(req.toCommand())
        return CommonResponse.success(UsePointRes(point))
    }
}

data class UsePointReq(
    @field:NotNull
    val storeId:Long,
    @field:Pattern(regexp = "[1-9][0-9]{9}")
    val barcode:String,
    @field:NotNull
    val pointAmount:Long
) {
    fun toCommand() = UsePointCommand(StoreId(storeId), barcode, pointAmount)
}

data class UsePointRes(
    val category: StoreCategory,
    val currentPoint:Long
) {
    constructor(point: Point) : this(category = point.category, currentPoint = point.point)
}