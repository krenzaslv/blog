package db.farm

import java.util.UUID

import com.google.inject.Inject
import db.offer.Offer
import db.{UserAwareService, BaseService}
import reactivemongo.api.commands.WriteResult
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class FarmService @Inject()(farmDao: FarmDao) extends BaseService[Farm](farmDao) with UserAwareService[Farm] {

  //TODO: find a cleaner way
  override def save(newFarm: Farm): Future[Farm] = {
    farmDao.find(newFarm.farmID).flatMap {
      case Some(farm) =>
        farmDao.update(farm.copy(
          name = newFarm.name,
          description = newFarm.description,
          website = newFarm.website,
          email = newFarm.email,
          offers = newFarm.offers
        ))
      case None =>
        farmDao.save(Farm(
          farmID = UUID.randomUUID(),
          name = newFarm.name,
          description = newFarm.description,
          website = newFarm.website,
          email = newFarm.email,
          offers = newFarm.offers,
          ownerID = newFarm.ownerID
        ))
    }
  }

  override def deleteById(userID: UUID, farmID: UUID): Future[WriteResult] = {
    farmDao.find(farmID).flatMap {
      case Some(farm) if farm.ownerID == userID => farmDao.delete(farmID)
    }
  }

  def addOffer(farmId: UUID, offer: Offer): Future[Offer] = farmDao.addOffer(farmId, offer)

  override def findById(userID: UUID): Future[List[Farm]] = farmDao.findByUserId(userID)

  def findByOfferId(offerID: UUID): Future[Option[Farm]] = farmDao.findByOfferId(offerID)

  override def saveById(id: UUID, entity: Farm): Future[Farm] = ???
}
