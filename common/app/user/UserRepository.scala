package user

import com.google.inject.Singleton
import core.BaseRepository

@Singleton
class UserRepository extends BaseRepository[User]