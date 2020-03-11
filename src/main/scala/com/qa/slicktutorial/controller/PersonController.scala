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
    personService.getPeople().foreach(personList => personService.returnPeople(personList.toList))
  }

  def getPerson(id: Int): Unit = {
    personService.getPerson(id)
  }


}
