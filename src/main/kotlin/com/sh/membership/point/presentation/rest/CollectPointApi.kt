package com.sh.membership.point.presentation.rest

import com.sh.membership.common.response.CommonResponse
import com.sh.membership.point.application.PointFacade
import com.sh.membership.point.domain.Point
import com.sh.membership.point.domain.collect.CollectPointCommand
import com.sh.membership.store.domain.StoreCategory
import com.sh.membership.store.domain.StoreId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RequestMapping("${PointApiUrl.COLLECT_POINT}")
@RestController
class CollectPointController(
    private val pointFacade: PointFacade
) {
    @PostMapping
    fun collectPoint(@Valid @RequestBody req: CollectPointReq) : CommonResponse<CollectPointRes> {
        val point = pointFacade.collectPoint(req.toCommand())
        return CommonResponse.success(CollectPointRes(point))
    }
}

data class CollectPointReq(
    @field:NotNull
    val storeId:Long,
    @field:Pattern(regexp = "[1-9][0-9]{9}")
    val barcode:String,
    @field:NotNull
    val pointAmount:Long
) {
    fun toCommand() = CollectPointCommand(StoreId(storeId), barcode, pointAmount)
}

data class CollectPointRes(
    val category: StoreCategory,
    val currentPoint:Long
) {
    constructor(point:Point) : this(category = point.category, currentPoint = point.point)
}