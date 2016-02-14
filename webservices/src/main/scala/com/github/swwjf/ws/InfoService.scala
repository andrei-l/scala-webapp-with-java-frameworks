package com.github.swwjf.ws

import com.github.swwjf.errorhandling.WSException
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConversions._
import scala.compat.java8.OptionConverters.RichOptionalGeneric

@Service
class InfoService @Autowired()(infoRepository: InfoRepository) {

  def fetchAllInformation(): List[InfoResponseDTO] = {
    def jpaToResponse(jpa: InfoJPA): InfoResponseDTO = {
      val response = InfoResponseDTO(
        createdDate = jpa.createdData.toString,
        updatedDate = Option(jpa.updatedDate).map(_.toString).orNull
      )
      BeanUtils.copyProperties(jpa, response)
      response
    }

    infoRepository
      .findAll()
      .map(jpaToResponse)
      .toList
  }

  def saveInfo(infoRequestDTO: InfoRequestDTO): Unit = {
    def requestToJPA(request: InfoRequestDTO): InfoJPA = {
      val jpa = new InfoJPA
      BeanUtils.copyProperties(request, jpa)
      jpa
    }

    try {
      infoRepository.save(requestToJPA(infoRequestDTO))
    } catch {
      case e: DataIntegrityViolationException => throw new WSException("Invalid/duplicate label")
    }
  }

  @Transactional
  def updateInfo(info: InfoRequestDTO): Unit = {
    val infoJPA = infoRepository
      .findOneByLabel(info.label)
      .asScala
      .getOrElse(throw new WSException(s"Failed to find info by label ${info.label}"))
    infoJPA.setMainDetails(info.mainDetails)
    infoJPA.setComments(info.comments)
  }

}