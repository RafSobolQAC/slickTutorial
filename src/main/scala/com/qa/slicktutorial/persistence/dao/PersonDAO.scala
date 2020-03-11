package com.qa.slicktutorial.persistence.dao

import com.qa.slicktutorial.persistence.domain.Person

object PersonDAO {
  def apply(firstName: String, lastName: String, age: Int): PersonDAO = new PersonDAO(-1, firstName, lastName, age)
  def apply(id: Int, personDao: PersonDAO): PersonDAO = new PersonDAO(id, personDao.firstName, personDao.lastName, personDao.age)
//  def apply(person: Person): PersonDAO = new PersonDAO(person.id.toString().toInt)
}
case class PersonDAO(id: Int, firstName: String, lastName: String, age: Int)

