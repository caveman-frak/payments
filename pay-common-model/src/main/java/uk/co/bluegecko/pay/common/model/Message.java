package uk.co.bluegecko.pay.common.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Message
{

	public enum Classification
	{
		ERROR, WARN, INFO, DIFF
	}

	@JsonProperty
	private final Map< Classification, Map< String, Set< String > > > messages;

	public Message()
	{
		messages = new EnumMap<>( Classification.class );
	}

	public boolean has( final Classification classification )
	{
		assert classification != null;

		return MapUtils.isNotEmpty( messages.get( classification ) );
	}

	public boolean has( final Classification... classifications )
	{
		assert ArrayUtils.isNotEmpty( classifications );

		for ( final Classification classification : classifications )
		{
			if ( has( classification ) )
			{
				return true;
			}
		}
		return false;
	}

	public boolean has( final Collection< Classification > classifications )
	{
		assert CollectionUtils.isNotEmpty( classifications );

		for ( final Classification classification : classifications )
		{
			if ( has( classification ) )
			{
				return true;
			}
		}
		return false;
	}

	public boolean has( final Classification classification, final String key )
	{
		assert classification != null && key != null;

		if ( has( classification ) )
		{
			final Map< String, Set< String > > map = messages.get( classification );
			if ( MapUtils.isNotEmpty( map ) )
			{
				return CollectionUtils.isNotEmpty( map.get( key ) );
			}
		}
		return false;
	}

	public boolean has( final String key )
	{
		assert key != null;

		for ( final Classification classification : Classification.values() )
		{
			final Map< String, Set< String > > map = messages.get( classification );
			if ( MapUtils.isNotEmpty( map ) && CollectionUtils.isNotEmpty( map.get( key ) ) )
			{
				return true;
			}
		}

		return false;
	}

	public Set< String > keys( final Classification classification )
	{
		if ( has( classification ) )
		{
			final Map< String, Set< String > > map = messages.get( classification );
			if ( map != null )
			{
				return Collections.unmodifiableSet( map.keySet() );
			}
		}
		return Collections.emptySet();
	}

	public Set< String > text( final Classification classification, final String key )
	{
		if ( has( classification, key ) )
		{
			final Set< String > set = messages.get( classification )
					.get( key );
			if ( set != null )
			{
				return Collections.unmodifiableSet( set );
			}
		}
		return Collections.emptySet();
	}

	public Set< String > add( final Classification classification, final String key, final String... text )
	{
		assert classification != null && key != null && ArrayUtils.isNotEmpty( text );

		if ( !has( classification ) )
		{
			final Map< String, Set< String > > map = new HashMap<>();
			messages.put( classification, map );
		}
		if ( !has( classification, key ) )
		{
			final Set< String > set = new HashSet<>();
			messages.get( classification )
					.put( key, set );
		}
		final Set< String > set = messages.get( classification )
				.get( key );

		set.addAll( Arrays.asList( text ) );
		return Collections.unmodifiableSet( set );
	}

	public void clear()
	{
		messages.clear();
	}

	public void clear( final Classification classification )
	{
		if ( has( classification ) )
		{
			messages.get( classification )
					.clear();
		}
	}

	public void clear( final Classification classification, final String key )
	{
		if ( has( classification, key ) )
		{
			messages.get( classification )
					.get( key )
					.clear();
		}
	}

	@Override
	public String toString()
	{
		final StringBuffer buffer = new StringBuffer();

		final ToStringStyle style = ToStringStyle.SHORT_PREFIX_STYLE;
		style.appendStart( buffer, this );

		printClassifications( buffer );
		buffer.append( "]" );
		return buffer.toString();
	}

	private void printClassifications( final StringBuffer buffer )
	{
		boolean populated = false;
		for ( final Classification classification : Classification.values() )
		{
			if ( has( classification ) )
			{
				populated = true;
				buffer.append( "\n\t" )
						.append( classification.name() );
				printKeys( buffer, classification, keys( classification ) );
			}
		}
		if ( populated )
		{
			buffer.append( "\n" );
		}
	}

	private void printKeys( final StringBuffer buffer, final Classification classification, final Set< String > keys )
	{
		final boolean multiLine = keys.size() > 1;

		for ( final String key : keys )
		{
			buffer.append( multiLine ? "\n\t\t" : "\t" )
					.append( key )
					.append( " : " )
					.append( printText( classification, key ) );
		}
	}

	private String printText( final Classification classification, final String key )
	{
		final StringJoiner joiner = new StringJoiner( "; " );
		for ( final String text : text( classification, key ) )
		{
			joiner.add( text );
		}
		return joiner.toString();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static final class Builder
	{

		private final Message message;

		protected Builder( final Message message )
		{
			this.message = message;
		}

		protected Builder()
		{
			this( new Message() );
		}

		protected Message message()
		{
			return message;
		}

		public ClassificationBuilder classification( final Classification classification )
		{
			return new ClassificationBuilder( this, classification );
		}

		public Message build()
		{
			return message;
		}
	}

	public static final class ClassificationBuilder
	{

		private final Builder builder;
		private final Classification classification;

		protected ClassificationBuilder( final Builder builder, final Classification classification )
		{
			this.builder = builder;
			this.classification = classification;
		}

		public Builder message( final String key, final String... text )
		{
			builder.message()
					.add( classification, key, text );

			return builder;
		}

	}

}
