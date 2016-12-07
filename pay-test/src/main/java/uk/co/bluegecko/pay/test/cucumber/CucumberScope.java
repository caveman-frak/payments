package uk.co.bluegecko.pay.test.cucumber;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Target(
	{ ElementType.TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@Scope( "cucumber-glue" )
@Component
public @interface CucumberScope
{
}
