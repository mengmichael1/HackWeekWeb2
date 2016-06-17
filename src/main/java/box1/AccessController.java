package box1;

import java.io.FileOutputStream;
import java.net.URISyntaxException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;
import com.box.sdk.ProgressListener;

import org.json.JSONObject;

@Controller
public class AccessController {
	
	private static final String clientId = "cbjx737c9ks9rnbgvoszukeex33kciom";
	private static final String clientSecret = "UeEwAsYq7vUM6vhquGu8GacwfzzmfB95";
	private static final int MAX_DEPTH = 1;
	private String authCode;
	private BoxAPIConnection apiConnection;
	
	@RequestMapping("/auth")
	public String auth(@RequestParam(value = "state", defaultValue = "access_denied") String state,
			@ModelAttribute @RequestParam(value = "code", defaultValue = "access_denied") String code, 
			@RequestParam(value = "error", defaultValue = "") String error, 
			@RequestParam(value = "error_description", defaultValue = "") String errorDescription, 
			Model model) throws URISyntaxException {
		
		if (!code.equals("access_denied")) {
			
			System.out.println("");
			System.out.println("Got GRANT response from user. Need to verify that CSRF security token received is"
					+ " same one given to user back in First Leg. State = " + state);
			
			//model.addAttribute("code", code);
			this.setAuthCode(code);
			apiConnection = new BoxAPIConnection(clientId, clientSecret, authCode); // BoxAPIConnection will auto refresh the access token if it has expired.
			
			return "redirect:/download";
		}
		
		return "redirect:/accessdenied";
	}
	
	
	@RequestMapping("/accessdenied")
	public String accessDenied(Model model) {
        return "accessdenied";
    }
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public String downloadForm(Model model) {
		
		DownloadForm newDownloadForm = new DownloadForm();

		BoxFolder rootFolder = BoxFolder.getRootFolder(apiConnection);
		String folderStructure = listFolder(rootFolder, 0);
		
		newDownloadForm.setFolderStructure(folderStructure);
		
		model.addAttribute("downloadForm", newDownloadForm);
		
		return "downloadForm"; // returning the name of a View, in this case "downloadForm", View renders HTML content
    }
	
	@RequestMapping(value="/download", method=RequestMethod.POST)
	public String formSubmit(@ModelAttribute DownloadForm downloadForm, Model model) {
		
		model.addAttribute("downloadForm", downloadForm);
		String fileToDownload = downloadForm.getNameFileToDownload();
		
		// *** Box SDK ***
		BoxUser.Info userInfo = BoxUser.getCurrentUser(apiConnection).getInfo();
		System.out.format("Account: %s <%s>\n\n", userInfo.getName(), userInfo.getLogin());

		String fileId = "";
		try {
			BoxFolder rootFolder = BoxFolder.getRootFolder(apiConnection);

			//System.out.println(listFolder(rootFolder, 0)); // listFolder works as intended

			fileId = getFileID(rootFolder, fileToDownload);

		} catch (BoxAPIException e) {
			System.err.format("The API returned the error code: %d\n\n%s", e.getResponseCode(), e.getResponse());
		}

		if (fileId.length() == 0) {
			System.out.println("Could not locate file to download: " + fileToDownload);
		} else {
			BoxFile file = new BoxFile(apiConnection, fileId);
			BoxFile.Info info = file.getInfo();

			try {
				FileOutputStream stream = new FileOutputStream("downloaded_" + info.getName());

				file.download(stream, new ProgressListener() {
					public void onProgressChanged(long numBytes, long totalBytes) {
						System.out.println("Downloading...");
					}
				});
				
				System.out.println("Download complete.");
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		
		
		return "downloadResult";
	}
	
	private String getFileID(BoxFolder folder, String fileName) {
		
		String accessToken = apiConnection.getAccessToken();
		
		// Search API call
		String url = "https://api.box.com/2.0/search";
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("query", fileName)
				.queryParam("limit", 1);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		HttpEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
		
		JSONObject json = new JSONObject(response.getBody().toString().trim());
		String fileID = json.getJSONArray("entries").getJSONObject(0).getString("id");
		
		return fileID;
	}

	private String listFolder(BoxFolder folder, int depth) {
		
		StringBuilder toReturn = new StringBuilder();
		
		for (BoxItem.Info itemInfo : folder) {
			String indent = "";
			for (int i = 0; i < depth; i++) {
				indent += "    ";
			}

			toReturn.append(indent + itemInfo.getName());
			toReturn.append("\n");
			
			if (itemInfo instanceof BoxFolder.Info) {
				BoxFolder childFolder = (BoxFolder) itemInfo.getResource();
				if (depth < MAX_DEPTH) {
					toReturn.append(listFolder(childFolder, depth + 1));
				}
			}
		}
		
		return toReturn.toString();
	}
	
	private void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}