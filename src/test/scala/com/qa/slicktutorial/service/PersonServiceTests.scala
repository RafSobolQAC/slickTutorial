package com.qa.slicktutorial.service
import com.qa.slicktutorial.UnitSpec
import com.qa.slicktutorial.persistence.dao.PersonDAO
import com.qa.slicktutorial.persistence.domain.Person
import com.qa.slicktutorial.service.PersonService
import com.qa.slicktutorial.utils.Connector
import org.scalatest.concurrent.ScalaFutures._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.mockito.{InjectMocks, MockitoAnnotations, Spy}
import org.scalatest.BeforeAndAfter
import org.scalatest.time.{Seconds, Span}
import slick.dbio.{Effect, NoStream}
import slick.jdbc
import slick.lifted.{Query, TableQuery}
import slick.sql.FixedSqlAction

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
  val mockTableQuery = mock(classOf[TableQuery[Person]])
  val mockFixedSqlAction = mock(classOf[FixedSqlAction[Option[Int], NoStream, Effect.Write]])
  val mockFutureSuccessful = mock(classOf[Future[Option[Int]]])
  val mockFuturePerson = mock(classOf[Future[PersonDAO]])
//  val mockQuery = mock(classOf[Query])
  before {
    spiedService = spy(service)
  }

  "the createPerson method" should "return a Future[Future[PersonDao]]" in {
    doReturn(mockTableQuery).when(mockTableQuery).++(any())
    doReturn(Future.successful(1)).when(mockDb).run(any())
    assert(spiedService.createPerson(mockDb, personDao).isInstanceOf[Future[Future[PersonDAO]]])
  }

  "the db.run query within it" should "return a Future[PersonDao]" in {
    doReturn(mockTableQuery).when(mockTableQuery).++(any())

    doReturn(mockFutureSuccessful).when(mockDb).run(any())
    doReturn(mockFuturePerson).when(mockFutureSuccessful).map(any())(any())
    spiedService.createPerson(mockDb, personDao)
    verify(mockDb).run(any())
    assert(spiedService.createPerson(mockDb, personDao).isInstanceOf[Future[Future[PersonDAO]]])
  }
}
