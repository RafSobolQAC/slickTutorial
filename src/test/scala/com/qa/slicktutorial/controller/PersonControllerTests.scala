package com.qa.slicktutorial.controller

import com.qa.slicktutorial.UnitSpec
import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.ServiceHelper
import org.scalatest.concurrent.ScalaFutures._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.mockito.{InjectMocks, MockitoAnnotations, Spy}
import org.scalatest.BeforeAndAfter
import org.scalatest.time.{Seconds, Span}

import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

class PersonControllerTests extends UnitSpec with BeforeAndAfter {
  val mockService: PersonService = mock(classOf[PersonService])
  val controller = new PersonController(mockService)
  var spyController: PersonController = spy(controller)
  val personDao: PersonDAO = PersonDAO.apply("a","b",12)
  val mockServiceHelper = mock(classOf[ServiceHelper])

  before {
    spyController = spy(controller)
  }
  "create person" should "access personService's create method" in {
    doReturn(Future.successful(Future.successful(personDao))).when(mockService).createPerson(any())
    spyController.createPerson(personDao)
    verify(mockService,times(1)).createPerson(personDao)
  }

  "get people" should "access personService's getPeople method" in {
    val listPeople = ListBuffer(personDao)
    doReturn(Future.successful(Future.successful(listPeople))).when(mockService).getPeople()
    doReturn(listPeople.toList).when(mockServiceHelper).returnPeople(any())
    spyController.getPeople()
    verify(mockService,times(1)).getPeople()
  }

  "get person" should "access personService's getPerson method" in {
    doReturn(Future.successful(Future.successful(personDao))).when(mockService).getPerson(anyInt())
    spyController.getPerson(1)
    verify(mockService,times(1)).getPerson(1)
  }

  "delete person" should "access personService's delete method" in {
    doReturn(Future.successful(Future.successful(1))).when(mockService).deletePerson(anyInt())
    spyController.deletePerson(1)
    verify(mockService,times(1)).deletePerson(1)
  }

  "update person" should "access personService's update method" in {
    doReturn(Future.successful(Future.successful(personDao))).when(mockService).updatePerson(anyInt(), any())
    spyController.updatePerson(1, personDao)
    verify(mockService,times(1)).updatePerson(1, personDao)

  }
}
