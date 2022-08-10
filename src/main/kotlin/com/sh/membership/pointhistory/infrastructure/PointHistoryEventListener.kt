package com.sh.membership.pointhistory.infrastructure

import com.sh.membership.pointhistory.domain.CollectPointEvent
import com.sh.membership.pointhistory.domain.UsePointEvent
import com.sh.membership.store.domain.GetStoreQuery
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PointHistoryEventListener(
    private val jpaRepository: PointHistoryJpaRepository,
    private val getStoreQuery: GetStoreQuery
) {
    @EventListener
    fun handle(event:CollectPointEvent) {
        val store = getStoreQuery.getStore(event.storeId).get()
        val pointHistory = event.toPointHistory(store.category, store.name)
        jpaRepository.save(pointHistory)
    }

    @EventListener
    fun handle(event:UsePointEvent) {
        val store = getStoreQuery.getStore(event.storeId).get()
        val pointHistory = event.toPointHistory(store.category, store.name)
        jpaRepository.save(pointHistory)
    }
}