package uk.co.bluegecko.pay.portfolio.service;


import java.util.List;

import uk.co.bluegecko.pay.portfolio.model.Batch;


public interface BatchService
{

	public Long createBatch( Batch batch );

	public Batch retreiveBatchById( long batchId );

	public List< Batch > retreiveBatches();

}
