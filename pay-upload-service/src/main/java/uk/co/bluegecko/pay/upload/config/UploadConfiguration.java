package uk.co.bluegecko.pay.upload.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import uk.co.bluegecko.pay.common.config.StandardConfiguration;


@Configuration
@ComponentScan( "uk.co.bluegecko.pay.bacs.std18" )
public class UploadConfiguration extends StandardConfiguration
{

}
