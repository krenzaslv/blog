package admin.controllers

import com.google.inject.{Inject, Singleton}
import core.{Reads, Writes}
import post.{Post, PostService}

@Singleton
class PostAdminController @Inject()(val service: PostService) extends BaseAdminController[Post] with Reads[Post] with Writes[Post]