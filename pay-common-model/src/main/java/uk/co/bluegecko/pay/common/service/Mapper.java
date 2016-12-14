package uk.co.bluegecko.pay.common.service;


import org.beanio.BeanReader;
import org.beanio.StreamFactory;


public interface Mapper< T extends ParsingContext >
{

	public String name();

	public StreamFactory addMapping( StreamFactory factory );

	public void map( final Object record, T context );

	public T newContext( BeanReader reader );

}
