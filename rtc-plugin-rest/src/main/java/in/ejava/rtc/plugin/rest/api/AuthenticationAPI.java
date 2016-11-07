package in.ejava.rtc.plugin.rest.api;

import in.ejava.rest.plugin.core.DataType;
import in.ejava.rest.plugin.core.HttpMethod;
import in.ejava.rest.plugin.core.client.Response;
import in.ejava.rest.plugin.core.client.RestClientFactory;
import in.ejava.rest.plugin.core.session.SessionCookieHolder;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationAPI extends BaseAPI {
	public AuthenticationAPI(String username) {
		super(username);
	}

	private static final String REST_FORM_URI = "authenticated/identity";
	private static final String REST_URI = "authenticated/j_security_check";

	public String authenticate(String password) {
		try {
			System.out.println("rtc server:" + getBaseUrl());
			RestClientFactory.getInstance().getRestClient();
			SessionCookieHolder.getSessionCookieMap().remove(
					getSessionIdentifier());

			Map<String, String> headers = new HashMap<String, String>();
			Response response = (Response) makeCall(buildUrl(REST_FORM_URI),
					HttpMethod.GET, headers, null, "", DataType.RAW,
					Response.class);

			String payload = "j_username=" + getSessionIdentifier()
					+ "&j_password=" + password;
			headers.put("Content-Type", "application/x-www-form-urlencoded");
			int postDataLength = payload.length();
			headers.put("Content-Length", Integer.toString(postDataLength));
			Response loginResponse = (Response) makeCall(buildUrl(REST_URI),
					HttpMethod.POST, headers, null, payload, DataType.RAW,
					Response.class, false);

			return (String) makeCall(buildUrl(REST_FORM_URI), HttpMethod.GET,
					headers, null, null, DataType.STRING, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
