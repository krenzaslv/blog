package models

import java.util.UUID

import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.commands.WriteResult
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future
import scala.tools.nsc.doc.model.Entity

//TODO: JSON coast to coast
trait BaseDao[E] {

  val TABLE_NAME : String

  val DEFAULT_MAX = 100

  def collection: JSONCollection

  def find(id: UUID): Future[Option[E]]

  def findAll: Future[List[E]]

  def save(entity: E): Future[E]

  def delete(id: UUID): Future[WriteResult]

  def update(entity: E): Future[E]
}