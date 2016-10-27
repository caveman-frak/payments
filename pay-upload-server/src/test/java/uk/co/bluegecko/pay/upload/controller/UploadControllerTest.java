package uk.co.bluegecko.pay.upload.controller;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.upload.UploadApplication;
import uk.co.bluegecko.pay.upload.service.UploadService;


@SpringBootTest( classes = UploadApplication.class, webEnvironment = WebEnvironment.MOCK )
@AutoConfigureMockMvc
public class UploadControllerTest extends TestHarness
{

	@Autowired
	MockMvc mvc;

	@MockBean
	private UploadService uploadService;

	@Before
	public void setUp() throws Exception
	{}

	@Test
	public final void testFileUpload() throws Exception
	{
		final MockMultipartFile multipartFile = new MockMultipartFile( "file", "test.txt", "text/plain",
				"Spring Framework".getBytes() );
		mvc.perform( fileUpload( UploadController.UPLOAD ).file( multipartFile ) )
				.andExpect( status().isAccepted() )
				.andExpect( header().string( "Location", "/status/0" ) );

		verify( uploadService ).processFile( multipartFile );
	}

	@Test
	public final void testFileStatus() throws Exception
	{
		when( uploadService.getJobStatus( 1L ) ).thenReturn( "COMLETED: 1" );

		mvc.perform( get( UploadController.STATUS, 1L ) )
				.andExpect( status().isOk() )
				.andExpect( content().string( "COMLETED: 1" ) );

		verify( uploadService ).getJobStatus( 1L );
	}

}
