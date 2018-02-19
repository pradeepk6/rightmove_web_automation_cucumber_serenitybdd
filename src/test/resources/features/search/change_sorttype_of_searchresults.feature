Feature: Change sort type of search results
  when a user searches for properties for sale or rent search-results are listed
  in default sort type(for eg. highest price).User should be able to view the
  same search-resutls by different sort types( for eg: lowest price, list-date etc)

  Scenario: change sort-type of sale search results from default to Newly Listed
    Given user has successfully searched properties for sale with different list dates
    When user changes the sort type of search results to "Newest Listed"
    Then search results sort order should change accordingly
    And check that the first non-featured search result is linked OK

  Scenario: change sort-type of rent search results from default to Newly Listed
    Given user has successfully searched properties for rent with different list dates
    When user changes the sort type of search results to "Newest Listed"
    Then search results sort order should change accordingly
    And check that the first non-featured search result is linked OK

