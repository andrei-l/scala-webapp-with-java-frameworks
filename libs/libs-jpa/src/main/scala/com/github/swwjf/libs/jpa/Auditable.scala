package com.github.swwjf.libs.jpa

import java.time.LocalDateTime
import javax.persistence.{Column, PrePersist, PreUpdate}

trait Auditable {
  @Column(name = "Created_Date")
  var createdData: LocalDateTime = _

  @Column(name = "Updated_Date")
  var updatedDate: LocalDateTime = _


  @PrePersist
  def touchForCreate(): Unit =
    createdData = LocalDateTime.now()

  @PreUpdate
  def touchForUpdate(): Unit =
    updatedDate = LocalDateTime.now()
}

