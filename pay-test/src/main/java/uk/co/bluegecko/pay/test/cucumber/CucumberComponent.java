package uk.co.bluegecko.pay.test.cucumber;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;


@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@CucumberProfile
@CucumberScope
@Component
public @interface CucumberComponent
{
}
