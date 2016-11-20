package uk.co.bluegecko.pay.v1.portfolio.wire;


import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = BureauUser.BureauUserBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public class BureauUser implements UserNumber
{

	@NotNull
	@Pattern( regexp = "B\\d{5}" )
	private final String userNumber;
	@JsonView( View.Detailed.class )
	@Singular
	private Set< String > serviceUsers;

}
