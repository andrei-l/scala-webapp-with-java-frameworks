package com.github.swwjf.errorhandling

private[swwjf] case class WSException(msg: String) extends RuntimeException(msg)
