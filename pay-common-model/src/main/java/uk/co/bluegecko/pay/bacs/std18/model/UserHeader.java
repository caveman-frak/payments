package uk.co.bluegecko.pay.bacs.std18.model;


import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;


@Value
@Builder
@Accessors( fluent = true )
public class UserHeader
{

	private final LocalDate processingDate;
	private final String dest;
	private final String currency;
	private final String country;
	private final String workCode;
	private final String file;
	private final String audit;

	public static final class UserHeaderBuilder implements BuilderConstants
	{

		public UserHeaderBuilder processingDate( final LocalDate processingDate )
		{
			this.processingDate = processingDate;

			return this;
		}

		public UserHeaderBuilder processingDate( final long julianDate )
		{
			processingDate = LocalDate.ofEpochDay( julianDate );

			return this;
		}

	}

}
