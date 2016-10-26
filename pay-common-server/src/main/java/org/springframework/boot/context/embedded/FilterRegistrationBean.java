package org.springframework.boot.context.embedded;


import javax.servlet.Filter;


@Deprecated
public class FilterRegistrationBean extends org.springframework.boot.web.servlet.FilterRegistrationBean
{

	public FilterRegistrationBean()
	{}

	public FilterRegistrationBean( final Filter filter,
			@SuppressWarnings( "deprecation" ) final ServletRegistrationBean... servletRegistrationBeans )
	{
		super( filter, servletRegistrationBeans );
	}

}
