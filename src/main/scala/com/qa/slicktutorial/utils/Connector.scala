package com.qa.slicktutorial.utils
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
object Connector {
  def apply() = new Connector
}
class Connector {
  val db = Database.forConfig("mysqlDB")
}
