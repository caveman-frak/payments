package uk.co.bluegecko.pay.portfolio.controller;


import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH;
import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH_BY_ID;
import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH_ID;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import uk.co.bluegecko.pay.portfolio.model.v1.WireService;
import uk.co.bluegecko.pay.portfolio.service.BatchService;
import uk.co.bluegecko.pay.portfolio.v1.wire.Batch;


@RestController
public class BatchController
{

	private final WireService wireService;
	private final BatchService batchService;

	@Autowired
	public BatchController( final WireService wireService, final BatchService batchService )
	{
		super();

		this.wireService = wireService;
		this.batchService = batchService;
	}

	@PostMapping( BATCH )
	public HttpEntity< Void > postBatch( @RequestBody final Batch batch )
	{
		final Long batchId = batchService.createBatch( wireService.fromWire( batch ) );

		final URI location = UriComponentsBuilder.fromPath( BATCH_BY_ID )
				.buildAndExpand( batchId )
				.toUri();
		return ResponseEntity.accepted()
				.location( location )
				.build();
	}

	@GetMapping( BATCH_BY_ID )
	public HttpEntity< Batch > getBatch( @PathVariable( BATCH_ID ) final long batchId )
	{
		final Batch batch = wireService.toWire( batchService.retreiveBatchById( batchId ) );

		return ResponseEntity.ok( batch );
	}

}
