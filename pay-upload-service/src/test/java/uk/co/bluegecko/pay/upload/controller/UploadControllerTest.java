package uk.co.bluegecko.pay.upload.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.FILE;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.STATUS;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.UPLOAD;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.upload.TestUploadApplication;
import uk.co.bluegecko.pay.upload.service.StreamingService;
import uk.co.bluegecko.pay.upload.service.UploadService;


@SpringBootTest( classes = TestUploadApplication.class, webEnvironment = WebEnvironment.MOCK )
@AutoConfigureMockMvc
public class UploadControllerTest extends TestHarness
{

	private static final String COMLETED = "COMLETED: 1";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UploadService uploadService;

	@MockBean
	private StreamingService streamingService;

	@Test
	public final void testFileUpload() throws Exception
	{
		final MockMultipartFile multipartFile = new MockMultipartFile( FILE, "test.txt", "text/plain",
				"Spring Framework".getBytes() );
		mvc.perform( fileUpload( UPLOAD ).file( multipartFile ) )
				.andExpect( status().isAccepted() )
				.andExpect( header().string( "Location", "http://localhost/status/0" ) );

		verify( uploadService ).processFile( multipartFile );
	}

	@Test
	public final void testFileStatus() throws Exception
	{
		when( uploadService.getJobStatus( 1L ) ).thenReturn( COMLETED );

		mvc.perform( get( STATUS, 1L ) )
				.andExpect( status().isOk() )
				.andExpect( content().string( COMLETED ) );

		verify( uploadService ).getJobStatus( 1L );
	}

}
