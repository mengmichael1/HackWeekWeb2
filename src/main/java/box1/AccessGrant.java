package box1;

public class AccessGrant {
	private String clientId = "cbjx737c9ks9rnbgvoszukeex33kciom";
	private String clientSecret = "UeEwAsYq7vUM6vhquGu8GacwfzzmfB95";
	private String grantType = "authorization_code";
	private String redirectUri = "http://localhost:8080/auth";
	private String code;

	public AccessGrant() {

	}

	public AccessGrant(String authCode) {
		this.code = authCode;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
