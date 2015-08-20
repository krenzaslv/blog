package controllers.admin

import com.google.inject.Inject
import db.farm.{Farm, FarmService}

class FarmAdminController @Inject()(farmService: FarmService) extends BaseAdminController[Farm](farmService) {

}