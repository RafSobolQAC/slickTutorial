package com.qa.slicktutorial.controller

import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.PersonParser
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class PersonController(personService: PersonService) {
  def createPerson(personDao: PersonDAO): Unit = {
    personService.createPerson(personDao)
  }


  def getPeople(): Unit = {
    personService.getPeople().onComplete {
      case Success(value) => value.onComplete {
        case Success(list) => personService.returnPeople(list.toList)
      }
    }
  }

  def getPerson(id: Int): Unit = {
    personService.getPerson(id).foreach(el => el.foreach(list => personService.returnPeople(List(list))))
  }

  def deletePerson(id: Int): Unit = {
    personService.deletePerson(id)
  }

  def updatePerson(id: Int, personDao: PersonDAO) = {
    personService.updatePerson(id, personDao)
  }


}
