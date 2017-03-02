package com.github.trod.company.support

import com.typesafe.config.ConfigFactory

trait TestConfig {
  val config = ConfigFactory.load

  object CommonSettings {
    val baseUrl = config.getString("common.baseUrl")
    val port = config.getInt("common.port")
    val apiPath = config.getString("common.apiPath")
    val wa_key = config.getString("common.wa_key")
    val WKDA = "wkda"
    val PAGE = "page"
    val PAGE_SIZE = "pageSize"
    val TOTAL_PAGE_COUNT = "totalPageCount"
  }

  object ManufacturerSettings {
    // configurable contraints
    var endpointUrl: String = config.getString("manufacturer.endpointUrl")
    var defaultLocale: String = config.getString("manufacturer.defaultLocale")
    // request constants
    val WA_KEY = "wa_key"
    val LOCALE = "locale"
    //response constants

    val getDefaultLocale = defaultLocale
  }

  object MainTypesSettings {
    // configurable contraints
    var endpointUrl: String = config.getString("main-types.endpointUrl")
    var defaultLocale: String = config.getString("main-types.defaultLocale")
    // request constants
    val WA_KEY = "wa_key"
    val LOCALE = "locale"
    val MANUFACTURER = "manufacturer"
    //response constants
    val getDefaultLocale = defaultLocale
  }

  object BuiltDatesSettings {
    // configurable contraints
    var endpointUrl: String = config.getString("main-types.endpointUrl")
    var defaultLocale: String = config.getString("main-types.defaultLocale")
    // request constants
    val WA_KEY = "wa_key"
    val LOCALE = "locale"
    val MANUFACTURER = "manufacturer"
    val MAIN_TYPE = "main-type"
    //response constants

    val getDefaultLocale = defaultLocale
  }

}
