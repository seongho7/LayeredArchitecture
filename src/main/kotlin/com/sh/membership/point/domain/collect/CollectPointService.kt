package com.sh.membership.point.domain.collect

import com.sh.membership.common.id.IdGenerator
import com.sh.membership.point.domain.*
import com.sh.membership.store.domain.StoreCategory

class CollectPointService(
    private val loadPointPort: LoadPointPort,
    private val savePointPort: SavePointPort,
    private val loadStorePort: LoadStorePort,
    private val validateBarcode: ValidateBarcode,
    private val idGenerator: IdGenerator
) : CollectPointUseCase {
    override fun collectPoint(command: CollectPointCommand) : Point {
        validateBarcode.validate(command.barcode)
        val store = loadStorePort.loadStore(command.storeId)
        val point = getOrNewPoint(command.barcode, store.category)
        point.collectPoint(command.pointAmount)
        return savePointPort.save(point)
    }

    private fun getOrNewPoint(barcode:String, category: StoreCategory) : Point {
        val oPoint = loadPointPort.loadPoint(barcode = barcode, category = category)
        if(oPoint.isPresent) {
            return oPoint.get()
        }
        return Point(id=idGenerator.nextId(), barcode = barcode, category = category)
    }
}