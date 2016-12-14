package uk.co.bluegecko.pay.bacs.std18.model;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@Value
@Builder( toBuilder = true )
@Accessors( fluent = true )
public class Account
{

	@Pattern( regexp = "\\d{6}" )
	private final String sortCode;
	@Pattern( regexp = "\\d{8}" )
	private final String number;
	@Length( max = 18 )
	@Pattern( regexp = "[A-Z0-9 \\-\\,]*" )
	@JsonView( View.Detailed.class )
	private final String name;
	@Pattern( regexp = "[0-9]?" )
	@JsonView( View.Detailed.class )
	private final String type;

	public static final class AccountBuilder
	{

		public AccountBuilder()
		{}

	}

}
