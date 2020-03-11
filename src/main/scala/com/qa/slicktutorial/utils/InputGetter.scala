package com.qa.slicktutorial.utils

import com.qa.slicktutorial.persistence.dao.PersonDAO

import scala.io.StdIn._
object InputGetter {
  val actions = Set("create","readone","readall","update","delete", "restart-entire-db")



  @scala.annotation.tailrec
  def getAction: String = {
    println("Possible actions: ")
    actions.foreach(el => print(el+" "))
    println()
    val action = readLine()
    if (!actions.contains(action)) {
      getAction
    } else {
      action
    }
  }

  def stringInput(): String = {
    readLine()
  }

  @scala.annotation.tailrec
  def intInput(): Int = {
    try {
      readLine().toInt
    } catch {
      case _: NumberFormatException =>
        println("Please input an integer.")
        intInput()
    }
  }

  def getPersonDao: PersonDAO = {
    println("First name? ")
    val fn = stringInput()
    println("Surname? ")
    val sn = stringInput()
    println("Age? ")
    PersonDAO.apply(fn, sn, intInput())
  }

}
