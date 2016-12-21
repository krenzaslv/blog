package post

import com.google.inject.Singleton
import core.BaseRepository

@Singleton
class PostRepository extends BaseRepository[Post]