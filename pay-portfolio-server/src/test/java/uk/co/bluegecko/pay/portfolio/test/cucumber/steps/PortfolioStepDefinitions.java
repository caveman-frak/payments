package uk.co.bluegecko.pay.portfolio.test.cucumber.steps;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.bluegecko.pay.portfolio.test.cucumber.hooks.PortfolioWebClient;
import uk.co.bluegecko.pay.test.cucumber.CucumberProfile;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch;


@CucumberProfile
public class PortfolioStepDefinitions
{

	private Long batchId;
	private Batch batch;

	@Autowired
	private PortfolioWebClient webClient;

	@Given( "^that batch \"(\\d+?)\" has been prepared$" )
	public void thatBatchIdHasBeenPrepared( final Long batchId ) throws Throwable
	{
		this.batchId = batchId;
	}

	@When( "^the batch is retrieved$" )
	public void theBatchIsRetrieved() throws Throwable
	{
		batch = webClient.requestBatchById( batchId );
	}

	@Then( "^the should have an id of \"(\\d+?)\"$" )
	public void theBatchShouldHaveAnIdOf( final Long batchId ) throws Throwable
	{
		assertThat( batch.id(), is( batchId ) );
	}

}
