package org.springframework.boot.context.embedded;


import javax.servlet.Servlet;


@SuppressWarnings( "rawtypes" )
@Deprecated
public class ServletRegistrationBean extends org.springframework.boot.web.servlet.ServletRegistrationBean
{

	public ServletRegistrationBean()
	{}

	public ServletRegistrationBean( final Servlet servlet, final String... urlMappings )
	{
		this( servlet, true, urlMappings );
	}

	@SuppressWarnings( "unchecked" )
	public ServletRegistrationBean( final Servlet servlet, final boolean alwaysMapUrl, final String... urlMappings )
	{
		super( servlet, alwaysMapUrl, urlMappings );
	}

}
