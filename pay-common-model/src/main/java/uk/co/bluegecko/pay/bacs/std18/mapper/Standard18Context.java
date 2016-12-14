package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.util.concurrent.atomic.AtomicInteger;

import org.beanio.BeanReader;

import uk.co.bluegecko.pay.common.service.base.AbstractParsingContext;


public class Standard18Context extends AbstractParsingContext
{

	private final AtomicInteger index;

	public Standard18Context( final BeanReader reader )
	{
		super( reader );

		index = new AtomicInteger( 0 );
	}

	public int index()
	{

		return index.incrementAndGet();
	}

}
