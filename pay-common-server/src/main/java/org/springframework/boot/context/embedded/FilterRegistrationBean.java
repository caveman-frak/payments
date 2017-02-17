package org.springframework.boot.context.embedded;


import javax.servlet.Filter;


@SuppressWarnings( "rawtypes" )
@Deprecated
public class FilterRegistrationBean extends org.springframework.boot.web.servlet.FilterRegistrationBean
{

	public FilterRegistrationBean()
	{}

	@SuppressWarnings( "unchecked" )
	public FilterRegistrationBean( final Filter filter,
			@SuppressWarnings( "deprecation" ) final ServletRegistrationBean... servletRegistrationBeans )
	{
		super( filter, servletRegistrationBeans );
	}

}
