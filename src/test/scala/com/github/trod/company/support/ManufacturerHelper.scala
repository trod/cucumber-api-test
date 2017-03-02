package com.github.trod.company.support

import scala.collection.JavaConverters._

/**
 * Created by boris.wainberg on 01/03/17.
 */
trait ManufacturerHelper extends TestHelper {
  case class ManufacturerRequest(wa_key: String, locale: String,
                                 contentType: String = "application/json",
                                 urlConfigured: String = "") extends Request {
    // TODO: need to refactor this in order to form the param string from a map of parameters
    def paramString = {
      import ManufacturerSettings._
      "?" + WA_KEY + "=" + wa_key + "&" + LOCALE + "=" + locale
    }

    def url = if (urlConfigured.isEmpty) {
      ManufacturerSettings.endpointUrl + paramString
    } else {
      urlConfigured + paramString
    }
  }

  case class ManufacturerResponse(page: Int, pageSize: Int, totalPageCount: Int, wkda: Map[String, String])

  def getManufacturerResponse(request: Request): ManufacturerResponse = {
    import CommonSettings._
    implicit val formats = org.json4s.DefaultFormats

    val json = getRawResponse(request).body().jsonPath()

    ManufacturerResponse(json.get(PAGE), json.get(PAGE_SIZE), json.get(TOTAL_PAGE_COUNT),
      (json.getMap[String, String](WKDA)).asScala.toMap)
  }
}
