package com.qa.slicktutorial.persistence.domain

import slick.lifted.Tag
//import slick.model.
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global

class Person(tag: Tag) extends Table[(Int,String,String,Int)](tag,"person") {
  def id = column[Int]("id",O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("name")
  def lastName = column[String]("surname")
  def age = column[Int]("age")
  def * = (id,firstName,lastName,age)
}
