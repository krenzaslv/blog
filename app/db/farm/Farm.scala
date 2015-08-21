package db.farm

import java.util.UUID

import db.offer.Offer
import Offer._

//TODO: validate with forms
case class Farm(
                 farmID: UUID,
                 name: String,
                 description: Option[String],
                 website: Option[String],
                 email: Option[String],
                 ownerID: UUID,
                 offers: Option[List[Offer]]
                 )

object Farm {

  import play.api.libs.json._

  implicit val farmFormat = Json.format[Farm]
}
