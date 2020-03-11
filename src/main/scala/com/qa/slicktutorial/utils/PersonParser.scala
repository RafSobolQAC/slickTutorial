package com.qa.slicktutorial.utils

import com.qa.slicktutorial.persistence.dao.PersonDAO

object PersonParser {
  def parse(personDao: PersonDAO): Seq[(Int, String, String, Int)]= {
    Seq((personDao.id, personDao.firstName, personDao.lastName, personDao.age))
  }
  def parseWithId(personDao: PersonDAO, id: Int) = {
    Seq((id, personDao.firstName, personDao.lastName, personDao.age))
  }
}
