package com.qa.slicktutorial.controller

import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.{PersonParser, ServiceHelper}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

class PersonController(personService: PersonService) {
  val serviceHelper = new ServiceHelper
  def createPerson(personDao: PersonDAO): Unit = {
    personService.createPerson(personDao).foreach(el => el.foreach(elem => serviceHelper.returnPeople(List(elem))))
  }

  def getPeople(): Unit = {
    personService.getPeople().foreach(el => el.foreach(elem => serviceHelper.returnPeople(elem.toList)))
  }

  def getPerson(id: Int): Unit = {
    personService.getPerson(id).foreach(el => el.foreach(list => serviceHelper.returnPeople(List(list))))
  }

  def deletePerson(id: Int): Unit = {
    personService.deletePerson(id)
  }

  def updatePerson(id: Int, personDao: PersonDAO): Unit = {
    personService.updatePerson(id, personDao).foreach(el => el.foreach(elem => serviceHelper.returnPeople(List(elem))))
  }


}
