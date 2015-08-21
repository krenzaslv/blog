package db.offer


import java.util.UUID

import com.google.inject.Inject
import db.BaseService
import db.farm.FarmService
import reactivemongo.api.commands.WriteResult
import Offer._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class OfferService @Inject()(farmService: FarmService, offerDao: OfferDao) extends BaseService[Offer](offerDao) {

  override def save(entity: Offer): Future[Offer] = ???

  def save(userID: UUID, farmID: UUID, offer: Offer): Future[Offer] = {
    farmService.find(farmID).flatMap {
      case Some(farm) if farm.ownerID == userID => farmService.addOffer(farmID, offer)
    }
  }

  def findById(userID: UUID): Future[List[Offer]] = ???

  def delete(userID: UUID, offerID: UUID): Future[WriteResult] = {
    farmService.findByOfferId(offerID).flatMap {
      case Some(farm) if farm.ownerID == userID => offerDao.delete(offerID)
    }
  }
}
