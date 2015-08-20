package forms

case class SignUpForm(
                       firstName: String,
                       lastName: String,
                       email: String,
                       password: String
                       )

object SignUpForm {

  import play.api.libs.json.Json

  implicit val signupFormat = Json.format[SignUpForm]
}

