package uk.co.bluegecko.pay.bacs.std18.model;


import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@Value
@Builder
@Accessors( fluent = true )
public class EndOfFile2
{

	private final String format;
	private final String block;
	private final String record;
	private final String offset;

}
