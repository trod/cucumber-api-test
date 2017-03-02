package com.github.trod.company.stepdefinitions

import com.github.trod.company.support.{BuiltDatesHelper, MainTypesHelper, ManufacturerHelper}
import cucumber.api.scala.{EN, ScalaDsl}
import cucumber.api.{DataTable, Scenario}
import org.scalatest.Matchers
import scala.collection.JavaConverters._

//TODO Refactor step logic to get rid of duplicating code
class CarSelectionSteps extends ScalaDsl with EN with Matchers with ManufacturerHelper with MainTypesHelper with BuiltDatesHelper {
  var manufacturerRequest: ManufacturerRequest = _
  var manufacturerResponse: ManufacturerResponse = _
  var mainTypesRequest: MainTypesRequest = _
  var mainTypesResponse: MainTypesResponse = _
  var builtDatesRequest: BuiltDatesRequest = _
  var builtDatesResponse: BuiltDatesResponse = _
  Before("@my-tag") { scenario: Scenario =>
    initRestAssured()
  }

  Given( """^User compose (manufacturer|main-types|built-dates) request with (valid|invalid|empty) secret key""") {
    (requestType: String, keyType: String) => {
      requestType match {
        case "manufacturer" => {
          keyType match {
            case "valid" => manufacturerRequest = ManufacturerRequest(CommonSettings.wa_key, ManufacturerSettings.defaultLocale)
            case "invalid" => manufacturerRequest = ManufacturerRequest("somerandomtext", ManufacturerSettings.defaultLocale)
            case "empty" => manufacturerRequest = ManufacturerRequest("", ManufacturerSettings.defaultLocale)
          }
        }
        case "main-types" => {
          keyType match {
            case "valid" => mainTypesRequest = MainTypesRequest(CommonSettings.wa_key, MainTypesSettings.defaultLocale)
            case "invalid" => mainTypesRequest = MainTypesRequest("somerandomtext", MainTypesSettings.defaultLocale)
            case "empty" => mainTypesRequest = MainTypesRequest("", MainTypesSettings.defaultLocale)
          }
        }
        case "built-dates" => {
          keyType match {
            case "valid" => builtDatesRequest = BuiltDatesRequest(CommonSettings.wa_key, BuiltDatesSettings.defaultLocale)
            case "invalid" => builtDatesRequest = BuiltDatesRequest("somerandomtext", BuiltDatesSettings.defaultLocale)
            case "empty" => builtDatesRequest = BuiltDatesRequest("", BuiltDatesSettings.defaultLocale)
          }
        }
      }
    }
  }

  And( """^User set following parameters for (manufacturer|main-types|built-dates) request""") {
    (requestType: String, params: DataTable) => {
      requestType match {
        case "main-types" => {
          val p = params.raw().asScala.toList.map(a => a.asScala.toList).collect { case List(a, b) => (a, b) }.toMap
          mainTypesRequest = mainTypesRequest.copy(manufacturer = p.get("manufacturer").getOrElse(""))
        }
        case "built-dates" => {
          val p = params.raw().asScala.toList.map(a => a.asScala.toList).collect { case List(a, b) => (a, b) }.toMap
          builtDatesRequest = builtDatesRequest.copy(manufacturer = p.get("manufacturer").getOrElse(""),
            mainType = p.get("main-types").getOrElse(""))
        }
        case "manufacturer" => {}
      }
    }
  }

  //TODO: Check if it's possible to use config params in cucumber regexp
  When( """^user requests (/manufacturer|/main-types|/built-dates) endpoint""") {
    (endpoint: String) => {
      endpoint match {
        case "/manufacturer" => {
          checkResponseCode(manufacturerRequest, 200)
          manufacturerRequest = manufacturerRequest.copy(urlConfigured = endpoint)
          manufacturerResponse = getManufacturerResponse(manufacturerRequest)
        }
        case "/main-types" => {
          checkResponseCode(mainTypesRequest, 200)
          mainTypesRequest = mainTypesRequest.copy(urlConfigured = endpoint)
          mainTypesResponse = getMainTypesResponse(mainTypesRequest)
        }
        case "/built-dates" => {
          checkResponseCode(builtDatesRequest, 200)
          builtDatesRequest = builtDatesRequest.copy(urlConfigured = endpoint)
          builtDatesResponse = getBuiltDatesResponse(builtDatesRequest)
        }
      }
    }
  }

  Then( """^returned (manufacturer|main-types|built-dates) response code is (..\d)""") {
    (requestType: String, expectedCode: Int) => {
      requestType match {
        case "manufacturer" => checkResponseCode(manufacturerRequest, expectedCode)
        case "main-types" => checkResponseCode(mainTypesRequest, expectedCode)
        case "built-dates" => checkResponseCode(builtDatesRequest, expectedCode)
      }
    }
  }

  Then( """^returned (manufacturer|main-types|built-dates) response contains following:""") {
    (requestType: String, table: DataTable) =>
      val expectedData = table.raw().asScala.toList
        .map { a => a.asScala.toList }
        .collect { case List(a, b) => (a, b) }
        .toMap
      assert(expectedData.toSet subsetOf {
        requestType match {
          case "manufacturer" => manufacturerResponse.wkda.toSet
          case "main-types" => mainTypesResponse.wkda.toSet
          case "built-dates" => builtDatesResponse.wkda.toSet
        }
      }, s"The list of returned ${requestType} doesn't contain expected ones.")
  }
}
