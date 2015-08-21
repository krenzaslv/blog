package db.offer

import java.util.UUID

import com.google.inject.Inject
import db.offer.Offer._
import play.api.libs.json._
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.ReadPreference
import reactivemongo.api.commands.WriteResult
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._

class OfferDaoMongo @Inject()(reactiveMongoApi: ReactiveMongoApi) extends OfferDao {

  override def collection: JSONCollection = reactiveMongoApi.db.collection(TABLE_NAME)

  override def update(offer: Offer): Future[Offer] = {
    collection.update(Json.obj("offerID" -> offer.offerID.toString), offer)
    Future.successful(offer)
  }

  override def findAll: Future[List[Offer]] = {
    collection.
      find(Json.obj("offers" -> Json.obj())).
      cursor[Offer](readPreference = ReadPreference.primary).
      collect[List](DEFAULT_MAX)
  }

  override def delete(offerId: UUID): Future[WriteResult] = collection.remove(Json.obj("offerId" -> offerId))

  override def save(offer: Offer): Future[Offer] = ??? // Managed by Farmdao

  override def find(offerID: UUID): Future[Option[Offer]] = collection.find(Json.obj("offerID" -> offerID)).one[Offer]

  def save(farmId: UUID, offer: Offer): Future[Offer] = {
    collection.update(Json.obj("farmID" -> farmId),
      Json.obj("$push" -> Json.obj("offers" -> offer)))
    Future.successful(offer)
  }
}
