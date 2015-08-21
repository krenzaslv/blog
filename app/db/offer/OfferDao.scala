package db.offer

import com.google.inject.ImplementedBy
import db.BaseDao

@ImplementedBy(classOf[OfferDaoMongo])
trait OfferDao extends BaseDao[Offer] {

  override val TABLE_NAME = "farms"
}
