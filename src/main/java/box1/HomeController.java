package box1;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class HomeController {
	
	private static final String clientId = "cbjx737c9ks9rnbgvoszukeex33kciom";
	private static final String state = "security_token%3DKnhMJatFipTAnM0nHlZA";
	
	@RequestMapping("/home")
	public ResponseEntity<Object> redirect() throws URISyntaxException {
		
		String authUrl = "https://account.box.com/api/oauth2/authorize";
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authUrl);
		urlBuilder.queryParam("response_type", "code");
		urlBuilder.queryParam("client_id", clientId);
		urlBuilder.queryParam("state", state);
		
		URI urlLocation = urlBuilder.build(false).encode().toUri();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(urlLocation);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}
}



