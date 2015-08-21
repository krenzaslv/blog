package db.offer

import java.util.UUID


case class Offer(
                  offerID: UUID,
                  name: String,
                  description: Option[String],
                  price: Double
                  )

object Offer {

  import play.api.libs.json._

  implicit val offerFormat = Json.format[Offer]
}
