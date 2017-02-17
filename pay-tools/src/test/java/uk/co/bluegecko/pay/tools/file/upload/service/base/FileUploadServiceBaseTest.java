package uk.co.bluegecko.pay.tools.file.upload.service.base;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withCreatedEntity;
import static uk.co.bluegecko.pay.test.exception.ThrowableCaptor.capture;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;

import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.test.rule.FileSystemRule;
import uk.co.bluegecko.pay.tools.file.upload.cli.UploadCmdLine;


public class FileUploadServiceBaseTest extends TestHarness
{

	private static final String UPLOAD = "upload/";
	private static final String STATUS = "status/1";

	private static final String FILE_1 = "/test1.txt";
	private static final String FILE_2 = "/test2,txt";

	private static final List< String > LINES_1 = Arrays.asList( "Line 1.1", "Line 1.2" );
	private static final List< String > LINES_2 = Arrays.asList( "Line 2.1", "Line 2.2" );

	@Rule
	public final FileSystemRule fileSystemRule = new FileSystemRule();

	@Rule
	public WireMockRule serverRule = new WireMockRule();

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();
	private final MockRestServiceServer server = MockRestServiceServer.createServer( restTemplate );

	private FileUploadServiceBase fileUploadService;

	@Before
	public void setUp() throws Exception
	{
		fileUploadService = new FileUploadServiceBase( restTemplate );
	}

	@After
	public void tearDown()
	{
		server.reset();
	}

	@Test
	public final void testCheckConnectionPass() throws IOException, URISyntaxException
	{
		fileUploadService.checkConnection( createURI( true ) );
	}

	@Test
	public final void testCheckConnectionFail()
	{
		final Throwable ex = capture( () -> fileUploadService.checkConnection( createURI( false ) ) );
		assertThat( ex, is( instanceOf( IOException.class ) ) );
	}

	@Test
	public final void testFileUpload() throws IOException, URISyntaxException
	{
		server.expect( once(), requestTo( createURI( false ).resolve( UPLOAD ) ) )
				.andExpect( method( HttpMethod.POST ) )
				.andRespond( withCreatedEntity( createURI( false ).resolve( STATUS ) ) );

		final Path file = fileSystemRule.getFileSystem()
				.getPath( FILE_1 );
		Files.write( file, LINES_1, StandardCharsets.UTF_8, StandardOpenOption.CREATE );

		fileUploadService.uploadFile( createURI( false ), file );

		server.verify();
	}

	@Test
	public final void testProcessFiles() throws IOException, URISyntaxException
	{
		server.expect( times( 2 ), requestTo( createURI( false ).resolve( UPLOAD ) ) )
				.andExpect( method( HttpMethod.POST ) )
				.andRespond( withCreatedEntity( createURI( false ).resolve( STATUS ) ) );

		final String[] fileNames =
			{ FILE_1, FILE_2 };
		try (final FileSystem fileSystem = fileSystemRule.getFileSystem())
		{
			Files.write( fileSystem.getPath( fileNames[0] ), LINES_1, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE );
			Files.write( fileSystem.getPath( fileNames[1] ), LINES_2, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE );

			fileUploadService.processFiles( Arrays.asList( fileNames )
					.stream(), "", fileSystem, createURI( false ) );
		}
		server.verify();
	}

	@Test
	public final void testProcessFilesCmdLine() throws IOException, URISyntaxException
	{
		final URI host = createURI( true );
		server.expect( once(), requestTo( host.resolve( UPLOAD ) ) )
				.andExpect( method( HttpMethod.POST ) )
				.andRespond( withCreatedEntity( host.resolve( STATUS ) ) );

		final Cli< UploadCmdLine > cli = CliFactory.createCli( UploadCmdLine.class );
		final String fileName = FILE_1;
		final UploadCmdLine cmdLine = cli.parseArguments( "--host", host.toASCIIString(), fileName );
		try (final FileSystem fileSystem = fileSystemRule.getFileSystem())
		{
			Files.write( fileSystem.getPath( fileName ), LINES_1, StandardCharsets.UTF_8, StandardOpenOption.CREATE );

			fileUploadService.processFiles( cmdLine, fileSystem );
		}
		server.verify();
	}

	private URI createURI( final boolean useServerPort ) throws URISyntaxException
	{
		final int port = useServerPort ? serverRule.port() : 9090;
		return new URI( "http", null, "localhost", port, "/", null, null );
	}

}
