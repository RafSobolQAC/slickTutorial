package com.qa.slicktutorial.utils

import com.qa.slicktutorial.persistence.domain.Person
import com.qa.slicktutorial.service.PersonService
import slick.lifted.TableQuery
import slick.lifted.TableQuery
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object Creator {
  val personTable = TableQuery[Person]
  val dropPeopleCmd = DBIO.seq(personTable.schema.drop)
  val initPersonCmd = DBIO.seq(personTable.schema.create)
  val personService = new PersonService
  val conn = new Connector
  val db = conn.db
  def dropDB(): Unit = {
    val dropFuture = Future{
      db.run(dropPeopleCmd)
    }

    Await.result(dropFuture, Duration.Inf).andThen{
      case Success(_) =>
        println("Dropping successful!")
        initialisePerson
      case Failure(error) =>
        println("Dropping failed! "+error.getMessage)
        initialisePerson
    }

  }

  def initialisePerson: Future[Unit] = {
    val setupFuture = Future {
      db.run(initPersonCmd)
    }
    Await.result(setupFuture, Duration.Inf).andThen {
      case Success(_) =>
        println("Initialising successful!")
      case Failure(error) =>
        println("Initialising table failed: "+error.getMessage)
    }
  }

}
