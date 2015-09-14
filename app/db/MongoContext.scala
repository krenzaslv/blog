package db

import reactivemongo.api.{DB, MongoDriver}
import scala.concurrent.ExecutionContext.Implicits.global

object MongoContext {
  val driver = new MongoDriver
  val connection = driver.connection(List("localhost"))
  def db: DB = connection("blog")
}
