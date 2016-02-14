package com.github.swwjf.libs.jpa

import javax.persistence.{Column, Version}

trait Versionable {
  @Version
  @Column(name = "Version")
  var version: Int = _
}
