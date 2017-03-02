package com.github.trod.company.support

import scala.collection.JavaConverters._

trait BuiltDatesHelper extends TestHelper {

  case class BuiltDatesRequest(wa_key: String, locale: String,
                               manufacturer: String = "150",
                               mainType: String = "ATS",
                               contentType: String = "application/json",
                               urlConfigured: String = "") extends Request {
    // TODO: need to refactor this to form the param string from a map of parameters
    // TODO: move common part of all endpoints to parent class
    def paramString = {
      import BuiltDatesSettings._
      "?" + WA_KEY + "=" + wa_key + "&" + LOCALE + "=" + locale + "&" +
        MANUFACTURER + "=" + manufacturer + "&" + MAIN_TYPE + "=" + mainType
    }

    def url = if (urlConfigured.isEmpty) {
      BuiltDatesSettings.endpointUrl + paramString
    } else {
      urlConfigured + paramString
    }
  }

  case class BuiltDatesResponse(page: Int, pageSize: Int, totalPageCount: Int, wkda: Map[String, String])

  def getBuiltDatesResponse(request: Request): BuiltDatesResponse = {
    import CommonSettings._
    implicit val formats = org.json4s.DefaultFormats

    val json = getRawResponse(request).body().jsonPath()

    BuiltDatesResponse(json.get(PAGE), json.get(PAGE_SIZE), json.get(TOTAL_PAGE_COUNT),
      (json.getMap[String, String] (WKDA)).asScala.toMap)
  }
}