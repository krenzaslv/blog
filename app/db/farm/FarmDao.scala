package db.farm

import java.util.UUID

import com.google.inject.ImplementedBy
import db.BaseDao

import scala.concurrent.Future

@ImplementedBy(classOf[FarmDaoMongo])
trait FarmDao extends BaseDao[Farm] {

  val TABLE_NAME = "farms"

  def find(id: UUID, userID: UUID): Future[Option[Farm]]

  def findAll(userID: UUID): Future[List[Farm]]
}
