package db.farm

import java.util.UUID

import com.google.inject.Inject
import db.offer.Offer
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._
import Farm._

class FarmDaoMongo @Inject()(reactiveMongoApi: ReactiveMongoApi) extends FarmDao {

  override def collection: JSONCollection = reactiveMongoApi.db.collection(TABLE_NAME)

  override def find(farmID: UUID): Future[Option[Farm]] = {
    collection.find(Json.obj("farmID" -> farmID)).one[Farm]
  }

  override def findAll: Future[List[Farm]] = {
    collection.
      find(Json.obj()).
      cursor[Farm](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  override def delete(farmID: UUID): Future[WriteResult] = {
    collection.remove(Json.obj("farmID" -> farmID))
  }

  override def update(farm: Farm): Future[Farm] = {
    collection.update(Json.obj("farmID" -> farm.farmID), farm)
    Future.successful(farm)
  }

  override def save(farm: Farm): Future[Farm] = {
    collection.insert(farm)
    Future.successful(farm)
  }


  override def findByUserId(userID: UUID): Future[List[Farm]] = {
    collection.
      find(Json.obj("ownerID" -> userID)).
      cursor[Farm](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  override def addOffer(farmId: UUID, offer: Offer): Future[Offer] = {
    collection.update(Json.obj("farmID" -> farmId.toString),
      Json.obj("$push" -> Json.obj("offers" -> offer)))
    Future.successful(offer)
  }

  //TODO generic find method with arguments and put in service
  def findOffers: Future[List[Offer]] = {
    collection.
      find(Json.obj()).
      cursor[Offer](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  //TODO generic find method with arguments and put in service
  def findOffersByFarm(farmID: UUID): Future[List[Offer]] = {
    collection.
      find(Json.obj("farmID" -> farmID.toString)).
      cursor[Offer](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  override def findByOfferId(offerID: UUID): Future[Option[Farm]] = {
    collection.
      find(Json.obj("offerID" -> offerID)).one[Farm]
  }
}
