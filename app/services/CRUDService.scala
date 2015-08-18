package services

import play.api.libs.json.JsResult

import scala.concurrent.Future

trait CRUDService[E] {

  def findById(id: String): Future[Option[E]]

  def findAll: Future[List[E]]

  def create(entity: E): Future[JsResult[E]]

  def update(id: String, entity: E): Future[JsResult[E]]

  def delete(id: String): Future[JsResult[String]]
}
