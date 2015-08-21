package formats.json

import play.api.libs.json.Json

case class OfferForm(
                      name: String,
                      description: Option[String],
                      price: Double
                      )

object OfferForm {

  implicit val offerFormat = Json.format[OfferForm]
}
