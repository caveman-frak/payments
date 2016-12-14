package uk.co.bluegecko.pay.common.service;


import org.beanio.BeanReader;


public interface Mapper
{

	public String name();

	public void map( final Object record, BeanReader reader );

}
