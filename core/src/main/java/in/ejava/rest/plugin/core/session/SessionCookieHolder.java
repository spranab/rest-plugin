package in.ejava.rest.plugin.core.session;

import java.util.HashMap;
import java.util.Map;

public class SessionCookieHolder {
	private static Map<String, Map<String, String>> sessionCookieMap;
	static {
		sessionCookieMap = new HashMap<String, Map<String, String>>();
	}

	public static Map<String, Map<String, String>> getSessionCookieMap() {
		return sessionCookieMap;
	}

	public static Map<String, String> getCookieMap(String sessionIdentifier) {
		if (sessionCookieMap.containsKey(sessionIdentifier)) {
			return sessionCookieMap.get(sessionIdentifier);
		}
		return null;
	}

	public static String getCookieString(String sessionIdentifier) {
		StringBuilder builder = new StringBuilder();
		if (sessionCookieMap.containsKey(sessionIdentifier)) {
			for (String key : sessionCookieMap.get(sessionIdentifier).keySet()) {
				try {
					builder.append(key);
					builder.append("=");
					builder.append(sessionCookieMap.get(sessionIdentifier).get(
							key));
					builder.append(";");
				} catch (Exception e) {
					System.out.println("Skipped cookie " + key);
				}

			}
		}
		return builder.toString();
	}

	public static void updateSessionCookies(String sessionIdentifier,
			Map<String, ? extends Iterable<String>> responseHeaders) {
		if (sessionCookieMap.containsKey(sessionIdentifier)) {
			updateCookies(sessionCookieMap.get(sessionIdentifier),
					responseHeaders);
		} else {
			Map<String, String> cookieMap = new HashMap<String, String>();
			updateCookies(cookieMap, responseHeaders);
			sessionCookieMap.put(sessionIdentifier, cookieMap);
		}
	}

	private static void updateCookies(Map<String, String> cookieMap,
			Map<String, ? extends Iterable<String>> responseHeaders) {
		if (responseHeaders.containsKey("Set-Cookie")) {
			for (String responseHeader : responseHeaders.get("Set-Cookie")) {
				String[] responseHeaderArray = responseHeader.split(";");
				for (String cookieInfo : responseHeaderArray) {
					try {
						String[] cookieKeyValPair = cookieInfo.split("=");
						cookieMap.put(cookieKeyValPair[0], cookieKeyValPair[1]);
					} catch (Exception e) {
						System.out.println("Skipped: " + cookieInfo);
					}
				}
			}
		}
	}

}
