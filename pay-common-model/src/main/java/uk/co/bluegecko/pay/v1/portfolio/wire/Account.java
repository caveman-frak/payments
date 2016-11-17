package uk.co.bluegecko.pay.v1.portfolio.wire;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = Account.AccountBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public class Account
{

	@Pattern( regexp = "\\d{6}" )
	private final String sortCode;
	@Pattern( regexp = "\\d{8}" )
	private final String number;
	@Length( max = 18 )
	@Pattern( regexp = "[A-Z \\-\\,]*" )
	@JsonView( View.Detailed.class )
	private final String name;
	@Pattern( regexp = "[0-9]?" )
	@JsonView( View.Detailed.class )
	private final String type;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class AccountBuilder
	{}

}
