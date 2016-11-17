package uk.co.bluegecko.pay.bacs.std18.model;


import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@Value
@Builder
@Accessors( fluent = true )
public class EndOfFile1
{

	private final String file;
	private final String set;
	private final String section;
	private final String sequence;
	private final String generation;
	private final String version;
	private final String created;
	private final String expires;
	private final String accessibility;
	private final String blockCount;
	private final String systemCode;
	private final String reserved;

}
