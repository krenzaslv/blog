package models

case class User(
                 firstName: String,
                 lastName: String,
                 products: Option[List[Product]]
                 )

case class Product(
                    name: String,
                    quantity: Int
                    )

object JsonFormats {

  import play.api.libs.json.Json

  implicit val productFormat = Json.format[Product]
  implicit val userFormat = Json.format[User]

}