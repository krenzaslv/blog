package testUtil

import core.{BaseModel, BaseRepository}
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}

import scala.concurrent.ExecutionContext.Implicits.global

trait BaseRepositoryTest[M <: BaseModel, T <: BaseRepository[M]] extends PlaySpec with OneAppPerSuite with ScalaFutures with BeforeAndAfter {

  implicit val defaultPatience = PatienceConfig(timeout = Span(2, Seconds), interval = Span(500, Millis))

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
