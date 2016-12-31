package page

import com.google.inject.{Inject, Singleton}
import core.BaseService

@Singleton
class PageService @Inject()(val repository: PageRepository) extends BaseService[Page]
