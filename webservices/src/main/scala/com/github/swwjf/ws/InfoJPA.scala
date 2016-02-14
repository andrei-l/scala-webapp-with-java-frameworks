package com.github.swwjf.ws

import javax.persistence.{Column, UniqueConstraint, Table, Entity}

import com.github.swwjf.libs.jpa.{Auditable, Versionable, Identifiable}

import scala.beans.BeanProperty

@Entity
@Table(name = "Information", uniqueConstraints = Array(
  new UniqueConstraint(columnNames = Array("Label"))
))
private[ws] class InfoJPA extends Identifiable with Versionable with Auditable {
  @BeanProperty
  @Column(name = "Label", nullable = false, unique = true)
  var label: String = _

  @BeanProperty
  @Column(name = "Main_Details", nullable = false)
  var mainDetails: String = _

  @BeanProperty
  @Column(name = "Comments")
  var comments: String = _
}
