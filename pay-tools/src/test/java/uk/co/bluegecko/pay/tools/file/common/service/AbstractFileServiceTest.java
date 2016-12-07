package uk.co.bluegecko.pay.tools.file.common.service;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.co.bluegecko.pay.test.rule.FileSystemRule;


public class AbstractFileServiceTest
{

	private static final String FILE = "/test.txt";
	private static final List< String > LINE = Arrays.asList( "Line 1", "Line 2" );

	@Rule
	public final FileSystemRule fileSystemRule = new FileSystemRule();

	private AbstractFileService fileService;

	@Before
	public void setUp() throws Exception
	{
		fileService = new AbstractFileService()
		{};
	}

	@Test
	public final void testFileIsValidPass() throws IOException
	{
		final Path file = fileSystemRule.getFileSystem()
				.getPath( FILE );
		Files.write( file, LINE, StandardCharsets.UTF_8, StandardOpenOption.CREATE );

		assertThat( fileService.isFileValid( file ), is( true ) );
	}

	@Test
	public final void testFileIsValidFailNoFile()
	{
		final Path file = fileSystemRule.getFileSystem()
				.getPath( FILE );

		assertThat( fileService.isFileValid( file ), is( false ) );
	}

	@Test
	public final void testFileIsValidFailIsDirectory() throws IOException
	{
		final Path file = fileSystemRule.getFileSystem()
				.getPath( "/test" );
		Files.createDirectory( file );

		assertThat( fileService.isFileValid( file ), is( false ) );
	}

}
