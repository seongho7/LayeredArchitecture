package com.sh.membership.point.application

import com.sh.membership.point.domain.Point
import com.sh.membership.point.domain.collect.CollectPointEvent
import com.sh.membership.point.domain.use.UsePointEvent
import com.sh.membership.point.domain.collect.CollectPointCommand
import com.sh.membership.point.domain.use.UsePointUseCase
import com.sh.membership.point.domain.collect.CollectPointUseCase
import com.sh.membership.point.domain.use.UsePointCommand
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.ApplicationEventPublisher
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.retry.support.RetryTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
class PointFacade(
    private val collectPointUseCase: CollectPointUseCase,
    private val usePointUseCase: UsePointUseCase,
    private val transactionTemplate: TransactionTemplate,
    private val eventPublisher: ApplicationEventPublisher,
    @Qualifier("pointModifyRetryTemplate")
    private val retryTemplate: RetryTemplate
) {

    fun collectPoint(command: CollectPointCommand) : Point {
        val point = retryTemplate.execute<Point, OptimisticLockingFailureException> {
            transactionTemplate.execute<Point> {
                collectPointUseCase.collectPoint(command)
            }
        }
        eventPublisher.publishEvent(CollectPointEvent(command, currentPoint = point.point))
        return point
    }

    fun usePoint(command: UsePointCommand) : Point {
        val point = retryTemplate.execute<Point, OptimisticLockingFailureException> {
            transactionTemplate.execute<Point> {
                usePointUseCase.usePoint(command)
            }
        }
        eventPublisher.publishEvent(UsePointEvent(command, currentPoint = point.point))
        return point
    }
}