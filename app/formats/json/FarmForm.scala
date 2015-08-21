package formats.json

import db.offer.Offer
import play.api.libs.json.Json
import db.offer.Offer._

case class FarmForm(
                     name: String,
                     description: Option[String],
                     website: Option[String],
                     email: Option[String],
                     offers: Option[List[Offer]]
                     )

object FarmForm {

  implicit val farmFormFormat = Json.format[FarmForm]
}
