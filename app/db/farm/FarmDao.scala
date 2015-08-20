package db.farm

import com.google.inject.ImplementedBy
import db.BaseDao

@ImplementedBy(classOf[FarmDaoMongo])
trait FarmDao extends BaseDao[Farm] {

  val TABLE_NAME = "farms"
}
