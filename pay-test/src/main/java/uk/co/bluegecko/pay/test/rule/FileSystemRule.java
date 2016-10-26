package uk.co.bluegecko.pay.test.rule;


import java.nio.file.FileSystem;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;


public final class FileSystemRule implements TestRule
{

	protected FileSystem fileSystem;
	protected final String name;
	protected final Configuration configuration;

	public FileSystemRule( final String name, final Configuration configuration )
	{
		this.name = name;
		this.configuration = configuration;
	}

	public FileSystemRule()
	{
		this( "test", Configuration.unix() );
	}

	public FileSystem getFileSystem()
	{
		return fileSystem;
	}

	@Override
	public Statement apply( final Statement base, final Description description )
	{
		return new Statement()
		{

			@Override
			public void evaluate() throws Throwable
			{
				try (FileSystem fileSystem = Jimfs.newFileSystem( name, configuration ))
				{
					FileSystemRule.this.fileSystem = fileSystem;

					base.evaluate();
				}
			}

		};

	}

}