package controllers

import com.google.inject.Inject
import models.user.{UserService, User}

class UserController @Inject()(userService: UserService) extends BaseCRUDController[User](userService) {

  //Signup handles create
  override def create = ???

}
