@my-tag
Feature: BuiltDates endpoint
  The main purpose of this feature is to make sure built-dates endpoint works
  in isolation from other endpoints. Basically this kind of testing is a perfect candidate to be moved to unit level


  Scenario: User gets a list of available main types for a given manufacturer when sends request with valid secret key
    Given User compose built-dates request with valid secret key
    And User set following parameters for built-dates request
      | manufacturer | 150 |
      | main-types   | ATS |
    When user requests /built-dates endpoint
    Then returned built-dates response code is 200
    And returned built-dates response contains following:
      | 2012 | 2012 |
      | 2016 | 2016 |

  Scenario: User gets a list of available manufactures when sends request with invalid secret key
    When User compose built-dates request with invalid secret key
    Then returned built-dates response code is 403

  Scenario: User gets a list of available manufactures when sends request with empty secret key
    When User compose built-dates request with empty secret key
    Then returned built-dates response code is 403
