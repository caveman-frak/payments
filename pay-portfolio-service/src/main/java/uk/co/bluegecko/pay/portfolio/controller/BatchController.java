package uk.co.bluegecko.pay.portfolio.controller;


import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH;
import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH_BY_ID;
import static uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping.BATCH_ID;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import uk.co.bluegecko.pay.Mapping.View;
import uk.co.bluegecko.pay.common.controller.AbstractController;
import uk.co.bluegecko.pay.portfolio.model.WireService;
import uk.co.bluegecko.pay.portfolio.service.BatchService;
import uk.co.bluegecko.pay.portfolio.v1.wire.Batch;


@RestController
public class BatchController extends AbstractController
{

	private final WireService< uk.co.bluegecko.pay.portfolio.model.Batch, Batch > wireService;
	private final BatchService batchService;

	@Autowired
	public BatchController( final WireService< uk.co.bluegecko.pay.portfolio.model.Batch, Batch > wireService,
			final BatchService batchService )
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

	@GetMapping( BATCH )
	public HttpEntity< MappingJacksonValue > getBatches(
			@RequestParam( name = View.PARAM, defaultValue = View.SUMMARY ) final Class< ? > view )
	{
		final List< Batch > batches = batchService.retreiveBatches()
				.stream()
				.map( wireService::toWire )
				.collect( Collectors.toList() );

		return ResponseEntity.ok( entityWithView( view, batches ) );
	}

}
