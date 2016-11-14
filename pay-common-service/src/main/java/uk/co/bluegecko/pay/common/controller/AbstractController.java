package uk.co.bluegecko.pay.common.controller;


import org.springframework.http.converter.json.MappingJacksonValue;


public abstract class AbstractController
{

	protected MappingJacksonValue entityWithView( final Class< ? > view, final Object entity )
	{
		final MappingJacksonValue entityMapping = new MappingJacksonValue( entity );
		entityMapping.setSerializationView( view );
		return entityMapping;
	}

}
