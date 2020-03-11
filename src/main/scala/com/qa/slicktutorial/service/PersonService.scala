package com.qa.slicktutorial.service

import com.qa.slicktutorial.Main.{connector, personTable}
import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.utils.PersonParser
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class PersonService {
  def createPerson(personDao: PersonDAO): Future[MySQLProfile.CountingInsertActionComposer[(Int, String, String, Int)]#MultiInsertResult] = {
    val insertPeople = Future {
      val query = personTable ++= PersonParser.parse(personDao)
      println(query.statements.head)
      connector.db.run(query)
    }
    Await.result(insertPeople, Duration.Inf).andThen {
      case Success(_) =>
        println("Query successful!")
      case Failure(error) => println("Something bad happened! " + error.getMessage)
    }
  }

  def waitForComplete(future: Future[Any]) = {
    future.onComplete {
      case Success(_) =>
        println("Complete!")
      case Failure(error) =>
        println("Failed! " + error.getMessage)
    }
  }

  def getPeople() = {
    val queryFuture = Future {
      var peopleList = new scala.collection.mutable.ListBuffer[PersonDAO]

      connector.db.run(personTable.result).map(_.foreach {
        case (id, firstName, lastName, age) =>
          peopleList += new PersonDAO(id, firstName, lastName, age)
      })

      peopleList

//        .onComplete {
//          case Success(_) =>
//            returnPeople(peopleList.toList)
//          case Failure(error) =>
//            println("Error on getPeople connecting!", error.getMessage)
//        }

    }
    queryFuture
//    waitForComplete(queryFuture)
  }

  def getPerson(id: Int): Unit = {
    var personToReturn = new PersonDAO(0, "a", "b", 1)
    val queryFuture = Future {
      connector.db.run(personTable.filter(_.id === id).result
        .map(person => personToReturn = new PersonDAO(person.head._1, person.head._2, person.head._3, person.head._4)))
        .onComplete {
          case Success(_) =>
            returnPeople(List(personToReturn))
          case Failure(error) =>
            println("Error on getPerson connecting!", error.getMessage)
        }
    }
    waitForComplete(queryFuture)
  }

  def returnPeople(people: List[PersonDAO]): List[PersonDAO] = {
    people.foreach(person => println(s"${person.id} ${person.firstName} ${person.lastName} ${person.age}"))

    people
  }

  def deletePerson(id: Int) = {
    val toDelete = personTable.filter(_.id === id).delete
    waitForComplete(connector.db.run(toDelete))
    println(toDelete.statements.head)
  }

  def updatePerson(id: Int, person: PersonDAO): Unit = {
    val toUpdate = personTable.filter(_.id === id)
    val queryUpdater = toUpdate.update(PersonParser.parseWithId(person, id).head)
    waitForComplete(connector.db.run(queryUpdater))
    println(queryUpdater.statements.head)

  }

}
