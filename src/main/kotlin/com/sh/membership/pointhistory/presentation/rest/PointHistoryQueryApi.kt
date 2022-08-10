package com.sh.membership.pointhistory.presentation.rest

import com.sh.membership.common.response.CommonResponse
import com.sh.membership.pointhistory.application.PointHistoryFacade
import com.sh.membership.pointhistory.domain.ActionType
import com.sh.membership.pointhistory.domain.PointHistory
import com.sh.membership.store.domain.StoreCategory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@RequestMapping("${PointHistoryApiUrl.SEARCH}")
@RestController
class PointHistoryQueryController(
    private val pointHistoryFacade: PointHistoryFacade
) {
    @GetMapping
    fun search(req:SearchPointHistoryReq) : CommonResponse<HistoryList> {
        val sort = Sort.by(Sort.Order(Sort.Direction.ASC, "approvedAt"))
        val pageable = PageRequest.of(req.page, req.pageSize, sort)

        val historyList = pointHistoryFacade.retrieve(req.barcode, req.startDate, req.endDate, pageable)
        return CommonResponse.success(
            HistoryList(historyList.map(::SearchPointHistoryRes))
        )
    }
}

data class SearchPointHistoryReq(
    val barcode:String,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val startDate:LocalDate,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val endDate:LocalDate,
    val page:Int,
    val pageSize:Int
)

data class HistoryList(
    val history:List<SearchPointHistoryRes>
)

data class SearchPointHistoryRes(
    val barcode:String,
    val type: ActionType,
    val storeCategory: StoreCategory,
    val storeName:String,
    val currentPoint:Long,
    val changedPoint:Long,
    val approvedAt: LocalDateTime
) {
    constructor(history:PointHistory) : this(
        barcode = history.barcode, type = history.type, storeCategory = history.storeCategory,
        storeName = history.storeName, currentPoint = history.currentPoint, changedPoint = history.changedPoint,
        approvedAt = history.approvedAt
    )
}