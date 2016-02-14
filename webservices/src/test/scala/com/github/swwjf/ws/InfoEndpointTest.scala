package com.github.swwjf.ws

import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation._
import com.github.springtestdbunit.assertion.DatabaseAssertionMode
import com.github.swwjf.WebServicesApplication
import com.github.swwjf.config.WSDBTestConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.{TestExecutionListeners, TestContextManager}
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers._
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(classOf[SpringJUnit4ClassRunner])
@TestExecutionListeners(Array(
  classOf[DbUnitTestExecutionListener],
  classOf[DependencyInjectionTestExecutionListener],
  classOf[TransactionalTestExecutionListener]
))
@WebAppConfiguration
@SpringApplicationConfiguration(Array(classOf[WebServicesApplication]))
@DbUnitConfiguration(databaseConnection = Array(WSDBTestConfig.ConnectionName))
class InfoEndpointTest {
  @Autowired
  private val webApplicationContext: WebApplicationContext = null

  new TestContextManager(getClass).prepareTestInstance(this)

  private val mockMvc: MockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()

  @Test
  @DatabaseTearDown(
    connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml"), `type` = DatabaseOperation.DELETE_ALL)
  @ExpectedDatabase(
    connection = WSDBTestConfig.ConnectionName, value = "classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  def testShouldSaveInfo(): Unit = {
    mockMvc.perform(
      post("/info")
        .contentType(MediaType.APPLICATION_JSON)
        .content(saveInfoRequest))
      .andExpect(status().isOk)
  }

  @Test
  @DatabaseSetup(connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml"))
  @DatabaseTearDown(
    connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml"), `type` = DatabaseOperation.DELETE_ALL)
  @ExpectedDatabase(
    connection = WSDBTestConfig.ConnectionName, value = "classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  def testShouldFailOnSaveInfoDueToDuplicateLabel(): Unit = {
    mockMvc.perform(
      post("/info")
        .contentType(MediaType.APPLICATION_JSON)
        .content(saveInfoRequest))
      .andExpect(status().isBadRequest)
      .andExpect(content().json(
        """
          {
            "error_message" : "Invalid/duplicate label"
          }
        """
      ))
  }

  @Test
  @DatabaseSetup(connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointSaveInfoExpectedResultTestDataSet.xml"))
  @DatabaseTearDown(
    connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointUpdateInfoExpectedResultTestDataSet.xml"), `type` = DatabaseOperation.DELETE_ALL)
  @ExpectedDatabase(
    connection = WSDBTestConfig.ConnectionName, value = "classpath:/InfoEndpointUpdateInfoExpectedResultTestDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  def testShouldUpdateInfo(): Unit = {
    mockMvc.perform(
      put("/info")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateInfoRequest))
      .andExpect(status().isOk)
  }

  @Test
  @DatabaseSetup(connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointGetAllSavedInfoExpectedResultTestDataSet.xml"))
  @DatabaseTearDown(
    connection = WSDBTestConfig.ConnectionName, value = Array("classpath:/InfoEndpointGetAllSavedInfoExpectedResultTestDataSet.xml"), `type` = DatabaseOperation.DELETE_ALL)
  @ExpectedDatabase(
    connection = WSDBTestConfig.ConnectionName, value = "classpath:/InfoEndpointGetAllSavedInfoExpectedResultTestDataSet.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  def testShouldGetAllSavedInfo(): Unit = {
    mockMvc.perform(
      get("/info"))
      .andExpect(status().isOk)
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().json(getAllSavedInfoResponse))
  }


  private val saveInfoRequest =
    """
      {
        "label": "sample",
        "main_details": "pin code: ****",
        "comments": "this is super important info"
      }
    """

  private val updateInfoRequest =
    """
      {
        "label": "sample",
        "main_details": "pin code: ****",
        "comments": "pin code has changed!"
      }
    """

  private val getAllSavedInfoResponse =
    """
      [

          {
              "updatedDate":"2016-02-14T18:07:04",
              "createdDate":"2016-01-14T18:07:04",
              "label":"sample",
              "comments":"this is super important info",
              "main_details":"pin code: ****"
          }

      ]
    """
}
