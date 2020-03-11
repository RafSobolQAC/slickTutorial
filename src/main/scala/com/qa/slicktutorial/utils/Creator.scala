package com.qa.slicktutorial.utils

import com.qa.slicktutorial.Main.{connector, db, dropPeopleCmd, initPersonCmd}
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}


class Creator {
  def dropDB(): Unit = {
    val dropFuture = Future{db.run(dropPeopleCmd)}
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
      connector.db.run(initPersonCmd)
    }
    Await.result(setupFuture, Duration.Inf).andThen {
      case Success(_) =>
        println("Initialising successful!")
      case Failure(error) =>
        println("Initialising table failed: "+error.getMessage)
    }
  }

}
