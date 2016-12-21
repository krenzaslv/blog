package user

import com.google.inject.{Inject, Singleton}
import core.BaseService

@Singleton
class UserService @Inject()(val repository: UserRepository) extends BaseService[User]