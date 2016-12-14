package uk.co.bluegecko.pay.bacs.std18.model;


import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@Value
@Builder
@Accessors( fluent = true )
public class Volume
{

	private final String serialNo;
	private final String accessibility;
	private final String userNumber;
	private final String label;

	public static final class VolumeBuilder
	{

		public VolumeBuilder()
		{}

	}

}
