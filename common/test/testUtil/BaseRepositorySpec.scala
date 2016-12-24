package testUtil

import core.{BaseModel, BaseRepository}
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

import scala.concurrent.ExecutionContext.Implicits.global

trait BaseRepositorySpec[M <: BaseModel, T <: BaseRepository[M]] extends PlaySpec with MustMatchers with ScalaFutures with BeforeAndAfter {

  implicit val defaultPatience = PatienceConfig(timeout = Span(2, Seconds), interval = Span(500, Millis))

  val app: Application = new GuiceApplicationBuilder().build()

  val repo: T

  def dropTable = repo.collection.flatMap(_.drop(false))

  after {
    val drop = dropTable
    whenReady(dropTable) {
      _ match {
        case false => note(s"Failed to drop collection ${repo.collectionName}")
        case _ =>
      }
    }
  }
}
