package uk.co.bluegecko.pay.tools.file.common.service;


import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractFileService
{

	private static final Logger logger = LoggerFactory.getLogger( AbstractFileService.class );

	protected boolean isFileValid( final Path file )
	{
		if ( !Files.exists( file, LinkOption.NOFOLLOW_LINKS ) || !Files.isRegularFile( file, LinkOption.NOFOLLOW_LINKS )
				|| !Files.isReadable( file ) )
		{
			logger.error( "Unable to locate or read file '{}'", file.toString() );
			return false;
		}
		else
		{
			return true;
		}
	}

}
