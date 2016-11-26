package uk.co.bluegecko.pay.portfolio.test.cucumber.hooks;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.bluegecko.pay.v1.portfolio.rest.BatchMapping.BATCH_BY_ID;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import uk.co.bluegecko.pay.test.cucumber.CucumberComponent;
import uk.co.bluegecko.pay.test.cucumber.CucumberWebClient;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch;


@CucumberComponent
public class PortfolioWebClient extends CucumberWebClient
{

	private final RestTemplate restTemplate;

	@Autowired
	public PortfolioWebClient( final RestTemplate restTemplate )
	{
		this.restTemplate = restTemplate;
	}

	public Batch requestBatchById( final Long batchId ) throws RestClientException, URISyntaxException
	{
		final ResponseEntity< Batch > result = restTemplate.getForEntity( host() + BATCH_BY_ID, Batch.class, batchId );

		assertThat( result.getStatusCode(), is( HttpStatus.OK ) );
		return result.getBody();
	}

}
