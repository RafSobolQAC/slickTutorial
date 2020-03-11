package com.qa.slicktutorial.persistence.dao

import com.qa.slicktutorial.UnitSpec

class PersonDAOTest extends UnitSpec{
  "A PersonDAO object" should "be creatable with a name, surname and age" in {
    val personDao = PersonDAO("Bobby","Tables",123)
    assert(personDao.age == 123)
    assert(personDao.firstName == "Bobby")
    assert(personDao.lastName == "Tables")
  }
}
