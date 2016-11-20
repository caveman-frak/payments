package uk.co.bluegecko.pay.portfolio.service.base;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.service.BatchService;


@Service
public class BatchServiceBase implements BatchService
{

	@Override
	public Long createBatch( final Batch batch )
	{
		return batch.id();
	}

	@Override
	public Batch retreiveBatchById( final long batchId )
	{
		return new BatchBase( batchId ).index( ( int ) ( 10 + batchId ) );
	}

	@Override
	public List< Batch > retreiveBatches()
	{
		return Arrays.asList( retreiveBatchById( 1 ), retreiveBatchById( 2 ) );
	}

}
