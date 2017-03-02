@my-tag
Feature: MainTypes endpoint
  The main purpose of this feature is to make sure main-types endpoint works
  in isolation from other endpoints. Basically this kind of testing is a perfect candidate to be moved to unit level

  Scenario: User gets a list of available main types for a given manufacturer when sends request with valid secret key
    Given User compose main-types request with valid secret key
    And User set following parameters for main-types request
      | manufacturer | 150 |

    When user requests /main-types endpoint
    Then returned main-types response code is 200
    And returned main-types response contains following:
      | ATS      | ATS      |
      | Escalade | Escalade |


  Scenario: User gets a list of available manufactures when sends request with invalid secret key
    When User compose main-types request with invalid secret key
    Then returned main-types response code is 403

  Scenario: User gets a list of available manufactures when sends request with empty secret key
    When User compose main-types request with empty secret key
    Then returned main-types response code is 403
