package formats.json

import play.api.libs.json.Json


case class SignInForm(
                       email: String,
                       password: String,
                       rememberMe: Boolean
                       )

object SignInForm {

  implicit val signInFormat = Json.format[SignInForm]
}
