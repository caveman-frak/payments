Feature: Portfolio Server, retrieve a single batch

Scenario: Retrieve a single batch
  Given that batch "2" has been prepared
  When the batch is retrieved
  Then the should have an id of "2"
