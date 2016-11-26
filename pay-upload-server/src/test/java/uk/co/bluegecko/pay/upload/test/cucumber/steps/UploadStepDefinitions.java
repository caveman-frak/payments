package uk.co.bluegecko.pay.upload.test.cucumber.steps;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.bluegecko.pay.test.cucumber.CucumberProfile;
import uk.co.bluegecko.pay.upload.test.cucumber.hooks.UploadWebClient;


@CucumberProfile
public class UploadStepDefinitions
{

	@Autowired
	private UploadWebClient webClient;

	private Path file;
	private URI jobId;

	public UploadStepDefinitions()
	{}

	@Given( "^the file \"(.*?)\" was prepared for upload$" )
	public void theFileWasPreparedForUpload( final String file ) throws Throwable
	{
		this.file = Paths.get( getClass().getClassLoader()
				.getResource( file )
				.toURI() );
	}

	@When( "^the upload job has been submitted$" )
	public void TheUploadJobHasBeenSubmitted() throws Throwable
	{
		jobId = webClient.submitFileForUpload( file );
	}

	@Then( "^the response should be \"(.*?)\"$" )
	public void theResponseShouldBe( final String message ) throws Throwable
	{
		assertThat( webClient.requestFileStatus( jobId ), is( message ) );
	}

}
