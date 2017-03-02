package com.github.trod.company.support

import scala.collection.JavaConverters._


trait MainTypesHelper extends TestHelper {

  case class MainTypesRequest(wa_key: String, locale: String,
                              manufacturer: String = "150",
                              contentType: String = "application/json",

                              urlConfigured: String = "") extends Request {
    // TODO: need to refactor this to form the param string from a map of parameters
    // TODO: move common part of all endpoints to parent class
    def paramString = {
      import MainTypesSettings._
      "?" + WA_KEY + "=" + wa_key + "&" + LOCALE + "=" + locale + "&" + MANUFACTURER + "=" + manufacturer
    }

    def url = if (urlConfigured.isEmpty) {
      MainTypesSettings.endpointUrl + paramString
    } else {
      urlConfigured + paramString
    }
  }

  case class MainTypesResponse(page: Int, pageSize: Int, totalPageCount: Int, wkda: Map[String, String])

  def getMainTypesResponse(request: Request): MainTypesResponse = {
    import CommonSettings._
    implicit val formats = org.json4s.DefaultFormats

    val json = getRawResponse(request).body().jsonPath()



    MainTypesResponse(json.get(PAGE), json.get(PAGE_SIZE), json.get(TOTAL_PAGE_COUNT),
      (json.getMap[String, String](WKDA)).asScala.toMap)
  }
}
