package com.github.swwjf.ws

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.web.bind.annotation._


@RestController
@RequestMapping(value = Array("/info"))
private[ws] class InfoEndpoint @Autowired()(infoService: InfoService) {

  @RequestMapping(method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def getAllSavedInfo: List[InfoResponseDTO] = infoService.fetchAllInformation()


  @RequestMapping(method = Array(RequestMethod.POST), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseStatus(HttpStatus.OK)
  def saveInfo(@RequestBody info: InfoRequestDTO): Unit = infoService.saveInfo(info)


  @RequestMapping(method = Array(RequestMethod.PUT), consumes = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseStatus(HttpStatus.OK)
  def updateInfoByLabel(@RequestBody info: InfoRequestDTO): Unit = infoService.updateInfo(info)

}
