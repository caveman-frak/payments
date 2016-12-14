package uk.co.bluegecko.pay.bacs.std18.model;


import java.math.BigDecimal;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;


@Value
@Builder
@Accessors( fluent = true )
public class UserTrailer
{

	private final BigDecimal debitValue;
	private final BigDecimal creditValue;
	private final Integer debitCount;
	private final Integer creditCount;
	private final Integer ddiCount;
	private final String serviceUser;

	public static final class UserTrailerBuilder implements BuilderConstants
	{

		public UserTrailerBuilder()
		{}

		public UserTrailerBuilder debitValue( final BigDecimal debitValue )
		{
			this.debitValue = debitValue;

			return this;
		}

		public UserTrailerBuilder debitValue( final String pence )
		{
			return debitValue( new BigDecimal( pence ).divide( HUNDRED ) );
		}

		public UserTrailerBuilder creditValue( final BigDecimal creditValue )
		{
			this.creditValue = creditValue;

			return this;
		}

		public UserTrailerBuilder creditValue( final String pence )
		{
			return creditValue( new BigDecimal( pence ).divide( HUNDRED ) );
		}

	}
}
