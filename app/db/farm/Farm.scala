package db.farm

import java.util.UUID

import play.api.libs.json.Json

//TODO: validate with forms
case class Farm(
                 farmID: UUID = UUID.randomUUID(),
                 name: String,
                 description: Option[String],
                 website: Option[String],
                 email: Option[String],
                 ownerID: UUID,
                 offers: Option[Seq[Offer]]
                 )

case class Offer(
                  name: String,
                  description: Option[String],
                  price: Double
                  )

object Farm {

  implicit val offerWrites = Json.format[Offer]
  implicit val farmWrites = Json.format[Farm]
}