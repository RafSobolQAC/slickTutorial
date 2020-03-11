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
  val connector: Connector = Connector.apply()
  val db = connector.db
  val personTable = TableQuery[Person]
  val dropPeopleCmd = DBIO.seq(personTable.schema.drop)
  val initPersonCmd = DBIO.seq(personTable.schema.create)
  val personService = new PersonService
  val creator = new Creator


  def main(args: Array[String]): Unit = {
//    val create = Future{creator.initialisePerson}
//    personService.updatePerson(2,PersonDAO.apply("Jimmy","Woods",37))
//    personService.deletePerson(-1)
    personService.getPerson(5)
    Thread.sleep(10000)
  }
}
