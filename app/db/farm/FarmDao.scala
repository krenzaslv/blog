package db.farm

import java.util.UUID

import com.google.inject.ImplementedBy
import db.BaseDao
import db.offer.Offer
import reactivemongo.api.commands.WriteResult

import scala.concurrent.Future

@ImplementedBy(classOf[FarmDaoMongo])
trait FarmDao extends BaseDao[Farm] {

  val TABLE_NAME = "farms"

  def findByOfferId(offerID: UUID): Future[Option[Farm]]

  def findByUserId(offerID: UUID): Future[List[Farm]]

  def addOffer(farmId: UUID, offer: Offer): Future[Offer]
}
