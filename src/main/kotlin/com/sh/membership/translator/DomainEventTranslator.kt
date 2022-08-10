package com.sh.membership.translator

import com.sh.membership.point.domain.collect.CollectPointEvent
import com.sh.membership.point.domain.use.UsePointEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DomainEventTranslator(
    private val eventPublisher: ApplicationEventPublisher
) {

    @EventListener
    fun translate(event: CollectPointEvent) {
        eventPublisher.publishEvent(com.sh.membership.pointhistory.domain.CollectPointEvent(
            storeId = event.storeId,
            barcode = event.barcode,
            currentPoint = event.currentPoint,
            collectAmount = event.collectAmount
        ))
    }

    @EventListener
    fun translate(event: UsePointEvent) {
        eventPublisher.publishEvent(com.sh.membership.pointhistory.domain.UsePointEvent(
            storeId = event.storeId,
            barcode = event.barcode,
            currentPoint = event.currentPoint,
            useAmount = event.useAmount
        ))
    }
}