package forms


import db.farm.{Offer, Farm}
import play.api.libs.json.Json

case class FarmForm(
                     name: String,
                     description: Option[String],
                     website: Option[String],
                     email: Option[String],
                     offers: Option[Seq[Offer]]
                     )

object FarmForm {

  implicit val offerFormat = Json.format[Offer]
  implicit val farmFormFormat = Json.format[FarmForm]
}
