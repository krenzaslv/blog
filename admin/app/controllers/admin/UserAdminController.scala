package controllers.admin

import com.google.inject.{Inject, Singleton}
import core.{Reads, Writes}
import user.{User, UserService}

@Singleton
class UserAdminController @Inject()(val service: UserService) extends BaseAdminController[User] with Reads[User] with Writes[User]