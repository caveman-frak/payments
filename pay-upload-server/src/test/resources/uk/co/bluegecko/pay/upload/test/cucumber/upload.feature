Feature: File Upload, single test

Scenario: Upload a file and check job status
  Given the file "sample-files/BTE_DEF.100101.10.1" was prepared for upload
  When the upload job has been submitted
  Then the response should be "COMPLETED: 0"
