package uk.co.bluegecko.pay.v1.portfolio.wire;


import java.util.Collections;
import java.util.Set;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = Batch.BatchBuilder.class )
@Value
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
	@Length( max = 50 )
	private final String name;
	private final String userNumber;
	private final String set;
	private final int section;
	private final int sequence;
	private final int generation;
	private final int version;
	@JsonView( View.Detailed.class )
	private final Set< Total > totals;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class BatchBuilder
	{

		private BatchBuilder()
		{
			totals = Collections.emptySet();
		}

	}

}
