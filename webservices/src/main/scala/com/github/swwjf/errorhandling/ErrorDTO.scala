package com.github.swwjf.errorhandling

import com.fasterxml.jackson.annotation.JsonProperty

private[errorhandling] case class ErrorDTO(@JsonProperty("error_message") errorMessage: String)