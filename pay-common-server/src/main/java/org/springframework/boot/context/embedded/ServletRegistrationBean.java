package org.springframework.boot.context.embedded;


import javax.servlet.Servlet;


@Deprecated
public class ServletRegistrationBean extends org.springframework.boot.web.servlet.ServletRegistrationBean
{

	public ServletRegistrationBean()
	{}

	public ServletRegistrationBean( final Servlet servlet, final String... urlMappings )
	{
		this( servlet, true, urlMappings );
	}

	public ServletRegistrationBean( final Servlet servlet, final boolean alwaysMapUrl, final String... urlMappings )
	{
		super( servlet, alwaysMapUrl, urlMappings );
	}

}
