package com.qa.slicktutorial.utils
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
object Connector {
  val db = Database.forConfig("mysqlDB")
}