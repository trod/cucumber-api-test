package com.github.trod.company.support

import io.restassured.RestAssured
import io.restassured.RestAssured._
import io.restassured.response.Response

/**
 * Created by boris.wainberg on 27/02/17.
 */
trait TestHelper extends TestConfig {

  abstract class Request(params: String*) {
    def url: String
    def paramString: String
  }

  def checkResponseCode(request: Request, expectedResponseCode: Int): Unit = {
    get(request.url).then().statusCode(expectedResponseCode).log().ifValidationFails()
  }

  def getRawResponse(request: Request): Response = {
    val response = get(request.url)
    get(request.url)
    response
  }

  def initRestAssured() = {
    RestAssured.baseURI = CommonSettings.baseUrl
    RestAssured.basePath = CommonSettings.apiPath
  }
}
