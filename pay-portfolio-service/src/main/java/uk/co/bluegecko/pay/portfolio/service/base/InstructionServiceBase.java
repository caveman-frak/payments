package uk.co.bluegecko.pay.portfolio.service.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.service.InstructionService;


@Service
public class InstructionServiceBase implements InstructionService
{

	private static final Logger logger = LoggerFactory.getLogger( InstructionService.class );

	@Override
	public void save( final Instruction instruction )
	{
		logger.info( "Saved: {}", instruction );
	}

}
