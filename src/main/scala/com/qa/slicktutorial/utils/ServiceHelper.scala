package com.qa.slicktutorial.utils

import com.qa.slicktutorial.persistence.dao.PersonDAO

import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class ServiceHelper {
  def waitForComplete(future: Future[Any]): Unit = {
    future.onComplete {
      case Success(_) =>
        println("Complete!")
      case Failure(error) =>
        println("Failed! " + error.getMessage)
    }
  }
  def returnPeople(people: List[PersonDAO]): List[PersonDAO] = {
    people.foreach(person => println(s"${person.id} ${person.firstName} ${person.lastName} ${person.age}"))

    people
  }

}
