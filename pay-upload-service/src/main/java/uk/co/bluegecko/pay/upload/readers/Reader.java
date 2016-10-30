package uk.co.bluegecko.pay.upload.readers;


import net.sf.flatpack.Record;


public interface Reader< T >
{

	public T read( Record record );

}
