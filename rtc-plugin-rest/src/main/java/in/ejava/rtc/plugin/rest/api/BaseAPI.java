package in.ejava.rtc.plugin.rest.api;

import in.ejava.rest.plugin.core.BaseClient;
import in.ejava.rest.plugin.core.util.PluginProperties;

public class BaseAPI extends BaseClient {

	public BaseAPI(String username) {
		super(username, PluginProperties.getPropery("rtc_server_url"));
	}

	protected String getUrl(String functionalUri, String param) {
		String url = getBaseUrl() + "rpt/repository/" + functionalUri + "/"
				+ param;
		System.out.println(url);
		return url;
	}

	protected String getUrl(String functionalUri) {
		String url = getBaseUrl() + "rpt/repository/" + functionalUri;
		System.out.println(url);
		return url;
	}

}
