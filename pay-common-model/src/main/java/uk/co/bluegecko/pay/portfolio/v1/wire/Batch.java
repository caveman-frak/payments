package uk.co.bluegecko.pay.portfolio.v1.wire;


import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = Batch.BatchBuilder.class )
@Data
@Builder
@Accessors( fluent = true )
public class Batch
{

	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Long id;
	@Min( 1 )
	private final int index;
	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Long portfolio;

	private final String setId;
	private final int fileSection;
	private final int generationNo;
	private final int generationVersion;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class BatchBuilder
	{}

}
