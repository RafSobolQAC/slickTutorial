package com.qa.slicktutorial

import com.qa.slicktutorial.controller.PersonController
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.{Connector, Creator, InputGetter}
import slick.jdbc

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class Runner {
  def getDb: jdbc.MySQLProfile.backend.Database = {
    val conn = new Connector
    conn.db
  }
  val db = getDb

  val personController = new PersonController(db, new PersonService())

  def doAThing(): Any = {
      InputGetter.getAction match {
        case "create" => personController.createPerson(InputGetter.getPersonDao)
        case "readall" => personController.getPeople()
        case "readone" =>
          println("Which ID to read? ")
          personController.getPerson(InputGetter.intInput())
        case "delete" =>
          println("Which ID to delete? ")
          personController.deletePerson(InputGetter.intInput())
        case "update" =>
          println("Which ID to update? ")
          val oldID = InputGetter.intInput()
          personController.updatePerson(oldID, InputGetter.getPersonDao)
        case "restart-entire-db" =>
          Creator.dropDB()
      }
    doAThing()
    }

}
