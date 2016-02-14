package com.github.swwjf.libs.jpa

import javax.persistence.{Column, GeneratedValue, Id}

trait Identifiable extends Serializable {
  @Id
  @Column(name = "ID")
  @GeneratedValue
  var id: Long = _
}
