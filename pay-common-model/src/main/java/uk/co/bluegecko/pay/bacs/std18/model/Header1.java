package uk.co.bluegecko.pay.bacs.std18.model;


import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@Value
@Builder
@Accessors( fluent = true )
public class Header1
{

	private final Row indicator;
	private final String file;
	private final String set;
	private final int section;
	private final int sequence;
	private final int generation;
	private final int version;
	private final LocalDate created;
	private final LocalDate expires;
	private final String accessibility;
	private final String blockCount;
	private final String systemCode;

	public static final class Header1Builder
	{

		public Header1Builder created( final LocalDate created )
		{
			this.created = created;

			return this;
		}

		public Header1Builder created( final long julianDate )
		{
			return created( LocalDate.ofEpochDay( julianDate ) );
		}

		public Header1Builder expires( final LocalDate expires )
		{
			this.expires = expires;

			return this;
		}

		public Header1Builder expires( final long julianDate )
		{
			return expires( LocalDate.ofEpochDay( julianDate ) );
		}

	}

}
