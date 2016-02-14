package com.github.swwjf.ws

import com.fasterxml.jackson.annotation.{JsonInclude, JsonProperty}

import scala.beans.BeanProperty

private[ws] trait BaseInfoRequest {
  @BeanProperty
  var label: String = _

  @JsonProperty("main_details")
  @BeanProperty
  var mainDetails: String = _

  @BeanProperty
  var comments: String = _
}

private[ws] class InfoRequestDTO extends BaseInfoRequest


@JsonInclude(JsonInclude.Include.NON_NULL)
private[ws] case class InfoResponseDTO(updatedDate: String, createdDate: String) extends BaseInfoRequest
