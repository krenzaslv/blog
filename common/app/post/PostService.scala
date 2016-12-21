package post

import com.google.inject.{Inject, Singleton}
import core.BaseService

@Singleton
class PostService @Inject()(val repository: PostRepository) extends BaseService[Post]
