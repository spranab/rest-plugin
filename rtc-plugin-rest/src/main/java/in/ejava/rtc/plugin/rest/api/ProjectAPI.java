package in.ejava.rtc.plugin.rest.api;

import in.ejava.rest.plugin.core.DataType;
import in.ejava.rest.plugin.core.HttpMethod;

import java.util.HashMap;

public class ProjectAPI extends BaseAPI {

	private static final String REST_URI = "foundation?fields=projectArea/projectArea/(name)";

	public ProjectAPI(String username) {
		super(username);
	}

	public String getProjects() {

		try {
			return (String) makeCall(getUrl(REST_URI), HttpMethod.GET,
					new HashMap<String, String>(), null, "dummy",
					DataType.STRING, String.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
