package uk.co.bluegecko.pay.portfolio.service.base;


import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.service.BatchService;


@Service
public class BatchServiceBase implements BatchService
{

	public BatchServiceBase()
	{
		super();
	}

	@Override
	public Long createBatch( final Batch batch )
	{
		return batch.id();
	}

	@Override
	public Batch retreiveBatchById( final long batchId )
	{
		return new BatchBase( batchId ).index( 15 );
	}

}
