package uk.co.bluegecko.pay.portfolio.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.bluegecko.pay.v1.portfolio.rest.BatchMapping.BATCH;
import static uk.co.bluegecko.pay.v1.portfolio.rest.BatchMapping.BATCH_BY_ID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import uk.co.bluegecko.pay.RestMapping.View;
import uk.co.bluegecko.pay.portfolio.TestPortfolioApplication;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch;


@SpringBootTest( classes = TestPortfolioApplication.class, webEnvironment = WebEnvironment.MOCK )
@AutoConfigureMockMvc
public class BatchControllerTest extends TestHarness
{

	@Autowired
	private MockMvc mvc;

	private Batch batch;

	@Before
	public void setUp() throws Exception
	{
		batch = Batch.builder()
				.id( 105L )
				.index( 10 )
				.build();
	}

	@Test
	public final void testPostBatch() throws Exception
	{
		final String body = write( batch );

		mvc.perform( post( BATCH ).contentType( MediaType.APPLICATION_JSON )
				.content( body ) )
				.andExpect( status().isAccepted() )
				.andExpect( header().string( "Location", "/batch/105" ) );
	}

	@Test
	public final void testGetBatchById() throws Exception
	{
		mvc.perform( get( BATCH_BY_ID, 101L ) )
				.andExpect( status().isOk() )
				.andExpect( content().json( "{\"id\":101,\"index\":111}" ) );
	}

	@Test
	public final void testGetBatchesWithDefaultView() throws Exception
	{
		mvc.perform( get( BATCH ) )
				.andExpect( status().isOk() )
				.andExpect( content().json( "[{\"index\":11},{\"index\":12}]" ) );
	}

	@Test
	public final void testGetBatchesWithDetailedView() throws Exception
	{
		mvc.perform( get( BATCH ).param( View.PARAM, View.DETAILED ) )
				.andExpect( status().isOk() )
				.andExpect( content().json( "[{\"id\":1,\"index\":11},{\"id\":2,\"index\":12}]" ) );
	}

}
