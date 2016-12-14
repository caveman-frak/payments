package uk.co.bluegecko.pay.common.service.base;


import org.beanio.BeanReader;

import uk.co.bluegecko.pay.common.service.ParsingContext;


public abstract class AbstractParsingContext implements ParsingContext
{

	private final BeanReader reader;

	public AbstractParsingContext( final BeanReader reader )
	{
		this.reader = reader;
	}

	@Override
	public BeanReader reader()
	{
		return reader;
	}

}
