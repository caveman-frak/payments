package uk.co.bluegecko.pay.portfolio.model.base;


import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Account;


@Data
@Accessors( fluent = true )
public final class AccountBase implements Account
{

	private final Long id;
	@Pattern( regexp = "\\d{6}" )
	private String sortCode;
	@Pattern( regexp = "\\d{8}" )
	private String number;
	@Length( max = 18 )
	@Pattern( regexp = "[A-Z \\-\\,]*" )
	private String name;
	@Pattern( regexp = "[0-9]?" )
	private String type;

}
