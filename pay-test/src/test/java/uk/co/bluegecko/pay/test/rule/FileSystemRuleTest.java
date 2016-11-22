package uk.co.bluegecko.pay.test.rule;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;


public class FileSystemRuleTest
{

	@Rule
	public final FileSystemRule fileSystemRule = new FileSystemRule();

	@Test
	public final void testFileIsValidPass() throws IOException
	{
		final Path file = fileSystemRule.getFileSystem()
				.getPath( "/test.txt" );
		Files.write( file, Arrays.asList( "Line 1", "Line 2" ), StandardCharsets.UTF_8, StandardOpenOption.CREATE );

		assertThat( Files.isReadable( file ), is( true ) );
	}

}
