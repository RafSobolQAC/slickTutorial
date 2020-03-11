package com.qa.slicktutorial.service
import com.qa.slicktutorial.UnitSpec
import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.Connector
import org.scalatest.concurrent.ScalaFutures._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.mockito.{InjectMocks, MockitoAnnotations, Spy}
import org.scalatest.BeforeAndAfter
import org.scalatest.time.{Seconds, Span}
import slick.jdbc

import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Random, Success}

class PersonServiceTests extends UnitSpec with BeforeAndAfter {
  val service: PersonService = new PersonService
  var spiedService: PersonService = spy(service)
  val personDao: PersonDAO = PersonDAO.apply("Bobby","Tables",123)
  val mockDb = mock(classOf[jdbc.MySQLProfile.backend.Database])

  before {
    spiedService = spy(service)
  }

  "the createPerson method" should "return a Future[Future[PersonDao]]" in {
    doReturn(mockDb).when(spiedService).getDb
    doReturn()
  }
}
