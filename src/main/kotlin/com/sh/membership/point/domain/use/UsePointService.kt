package com.sh.membership.point.domain.use

import com.sh.membership.point.domain.*
import com.sh.membership.store.domain.StoreCategory

class UsePointService(
    private val loadPointPort: LoadPointPort,
    private val savePointPort: SavePointPort,
    private val loadStorePort: LoadStorePort,
    private val validateBarcode: ValidateBarcode,
) : UsePointUseCase {
    override fun usePoint(command: UsePointCommand) : Point {
        validateBarcode.validate(command.barcode)
        val store = loadStorePort.loadStore(command.storeId)
        val point = getPoint(command.barcode, store.category)
        point.usePoint(command.pointAmount)
        return savePointPort.save(point)
    }

    private fun getPoint(barcode:String, category: StoreCategory) : Point {
        val oPoint = loadPointPort.loadPoint(barcode = barcode, category = category)
        if(oPoint.isPresent) {
            return oPoint.get()
        }
        throw PointNotEnoughException()
    }
}