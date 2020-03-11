package com.qa.slicktutorial

import com.qa.slicktutorial.controller.PersonController
import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.persistence.domain.Person
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.{Connector, Creator}
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


object Main {

  def main(args: Array[String]): Unit = {
    val runner = new Runner
    runner.doAThing()

    Thread.sleep(10000)
  }
}
