package uk.co.bluegecko.pay.v1.portfolio.rest;


public interface BatchMapping
{

	public static final String BATCH = "/batch/";
	public static final String BATCH_ID = "batchId";
	public static final String BATCH_BY_ID = BATCH + "{" + BATCH_ID + "}";

}
