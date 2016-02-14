package com.github.swwjf.ws

import java.util.Optional

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
private[ws] trait InfoRepository extends CrudRepository[InfoJPA, java.lang.Long] {
  def findOneByLabel(label: String): Optional[InfoJPA]
}
