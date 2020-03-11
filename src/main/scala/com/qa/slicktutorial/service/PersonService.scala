package com.qa.slicktutorial.service

import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.persistence.domain.Person
import com.qa.slicktutorial.utils.{Connector, PersonParser}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.TableQuery

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}
import slick.jdbc

class PersonService() {

  val personTable = TableQuery[Person]

  def getDb: jdbc.MySQLProfile.backend.Database = {
    val conn = new Connector
    conn.db
  }

  val db = getDb

  def createPerson(personDao: PersonDAO): Future[Future[PersonDAO]] = {
    val insertPeople = Future {
      val query = personTable ++= PersonParser.parse(personDao)
      println(query.statements.head)
      db.run(query).map(_ => personDao)
    }
    insertPeople
  }


  def getPeople(): Future[Future[ListBuffer[PersonDAO]]] = {
    val queryFuture = Future {
      var peopleList = new scala.collection.mutable.ListBuffer[PersonDAO]

      db.run(personTable.result).map(_.foreach {
        case (id, firstName, lastName, age) =>
          peopleList += new PersonDAO(id, firstName, lastName, age)
      }).map(_ => peopleList)

    }
    queryFuture
    //    waitForComplete(queryFuture)
  }

  def getPerson(id: Int): Future[Future[PersonDAO]] = {
    var personToReturn = new PersonDAO(0, "a", "b", 1)
    val queryFuture = Future {
      db.run(personTable.filter(_.id === id).result
        .map(person => personToReturn = new PersonDAO(person.head._1, person.head._2, person.head._3, person.head._4)))
        .map(_ => personToReturn)
    }
    queryFuture
  }


  def deletePerson(id: Int) = {
    val toDelete = personTable.filter(_.id === id).delete
    println(toDelete.statements.head)

    val queryFuture = Future {
      db.run(toDelete)
    }
    queryFuture
  }

  def updatePerson(id: Int, person: PersonDAO): Future[Future[PersonDAO]] = {
    val toUpdate = personTable.filter(_.id === id)
    val queryUpdater = toUpdate.update(PersonParser.parseWithId(person, id).head)
    val futureUpdate = Future {
      db.run(queryUpdater).map(_ => PersonDAO.apply(id, person))
    }
    println(queryUpdater.statements.head)
    futureUpdate

  }

}
