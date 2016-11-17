package uk.co.bluegecko.pay.bacs.std18.model;


import java.util.Arrays;
import java.util.List;


public interface TransactionCode
{

	public static final List< Class< ? extends TransactionCode > > CLASSES = Arrays.asList( CreditCode.class,
			DebitCode.class, AuddisCode.class );

	public String code();

	public enum CreditCode implements TransactionCode
	{
		CREDIT( "99" ), INTEREST( "Z4" ), DIVIDEND( "Z5" );

		private final String code;

		private CreditCode( final String code )
		{
			this.code = code;
		}

		@Override
		public String code()
		{
			return code;
		}
	}

	public enum DebitCode implements TransactionCode
	{
		FIRST( "01" ), REGULAR( "17" ), REPRESENT( "18" ), FINAL( "19" );

		private final String code;

		private DebitCode( final String code )
		{
			this.code = code;
		}

		@Override
		public String code()
		{
			return code;
		}
	}

	public enum AuddisCode implements TransactionCode
	{
		NEW( "0N" ), CANCEL( "0C" ), CONVERT( "0S" );

		private final String code;

		private AuddisCode( final String code )
		{
			this.code = code;
		}

		@Override
		public String code()
		{
			return code;
		}
	}

	public static < T extends Enum< ? extends TransactionCode > & TransactionCode > T byCode( final Class< T > klass,
			final String code )
	{
		for ( final T value : klass.getEnumConstants() )
		{
			if ( value.code()
					.equals( code ) )
			{
				return value;
			}
		}
		throw new IllegalArgumentException( "no-transaction-type-" + klass.getSimpleName() + "-with-code-" + code );
	}

	public static TransactionCode byCode( final String code )
	{
		for ( final Class< ? extends TransactionCode > klass : CLASSES )
		{
			for ( final TransactionCode value : klass.getEnumConstants() )
			{
				if ( value.code()
						.equals( code ) )
				{
					return value;
				}
			}
		}
		throw new IllegalArgumentException( "no-transaction-with-code-" + code );
	}

}
