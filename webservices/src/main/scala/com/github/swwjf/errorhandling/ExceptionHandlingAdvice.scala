package com.github.swwjf.errorhandling

import com.github.swwjf.errorhandling.ExceptionHandlingAdvice.{DefaultError, Logger, InvalidRequestError}
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseBody, ResponseStatus, ControllerAdvice}

@ControllerAdvice
private class ExceptionHandlingAdvice {
  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleWSException(e: WSException): ErrorDTO = ErrorDTO(e.getMessage)

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  def handleInvalidRequestException(e: HttpMessageNotReadableException): ErrorDTO = ErrorDTO(InvalidRequestError)

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  def handleThrowable(e: Throwable): ErrorDTO = {
    val errorMessage = Option(e.getMessage).getOrElse(DefaultError)
    Logger.error(errorMessage, e)
    ErrorDTO(DefaultError)
  }
}

private[errorhandling] object ExceptionHandlingAdvice {
  final val Logger = LoggerFactory.getLogger(classOf[ExceptionHandlingAdvice])
  final val DefaultError = "Internal Server Error"
  final val InvalidRequestError = "Invalid Request"
}


