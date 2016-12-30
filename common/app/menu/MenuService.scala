package menu

import com.google.inject.Inject
import core.BaseService

class MenuService @Inject()(val repository: MenuRepository) extends BaseService[Menu] {}
