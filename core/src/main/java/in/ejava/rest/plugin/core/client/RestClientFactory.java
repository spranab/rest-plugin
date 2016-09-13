package in.ejava.rest.plugin.core.client;

import in.ejava.rest.plugin.core.session.SessionCookieHolder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

public class RestClientFactory {
	private RestClient restClient;
	private static RestClientFactory instance;

	static {
		instance = new RestClientFactory();
		instance.restClient = new RestClient();
	}

	/**
	 * Private constructor to make the factory class as singleton
	 */
	private RestClientFactory() {
	}

	/**
	 * To get the instance for RestTemplateFactory
	 * 
	 * @return RestTemplateFactory
	 */
	public static RestClientFactory getInstance() {
		return instance;
	}

	public RestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(RestClient restClient) {
		this.restClient = restClient;
	}

	public static class RestClient {

		private RestClient() {
		}

		public Response put(String url, byte[] data,
				Map<String, String> headers, String sessionIdentifier)
				throws Exception {

			return execute("PUT", url, null, data, headers, sessionIdentifier);
		}

		public Response post(String url, byte[] data,
				Map<String, String> headers, String sessionIdentifier)
				throws Exception {

			return execute("POST", url, null, data, headers, sessionIdentifier);
		}

		public Response delete(String url, Map<String, String> headers,
				String sessionIdentifier) throws Exception {

			return execute("DELETE", url, null, null, headers,
					sessionIdentifier);
		}

		public Response get(String url, String queryString,
				Map<String, String> headers, String sessionIdentifier)
				throws Exception {

			return execute("GET", url, queryString, null, headers,
					sessionIdentifier);
		}

		private Response execute(String type, String urlStr,
				String queryString, byte[] data, Map<String, String> headers,
				String sessionIdentifier) throws Exception {

			if ((queryString != null) && !queryString.isEmpty()) {

				urlStr += "?" + queryString;
			}

			URL url = new URL(urlStr);
			HttpURLConnection con = null;
			if ("https".equalsIgnoreCase(url.getProtocol())) {
				HttpsURLConnection sslCon = (HttpsURLConnection) url
						.openConnection();
				sslCon.setSSLSocketFactory(getSSLSocketFactory());
				con = sslCon;
			} else {
				con = (HttpURLConnection) url.openConnection();
			}

			con.setRequestMethod(type);

			String cookieString = SessionCookieHolder
					.getCookieString(sessionIdentifier);

			prepareHttpRequest(con, headers, data, cookieString);
			con.connect();
			Response response = retrieveResponse(con);

			SessionCookieHolder.updateSessionCookies(sessionIdentifier,
					response.getResponseHeaders());

			return response;
		}

		/**
		 * Prepare request before sending
		 * 
		 * @param con
		 * @param headers
		 * @param bytes
		 * @param cookieString
		 * @throws IOException
		 */
		private void prepareHttpRequest(HttpURLConnection con,
				Map<String, String> headers, byte[] bytes, String cookieString)
				throws IOException {

			String contentType = null;

			// Send cookie information
			if ((cookieString != null) && !cookieString.isEmpty()) {

				con.setRequestProperty("Cookie", cookieString);
			}

			// Send data from headers
			if (headers != null) {
				// Resetting Content-Type with the one present in current
				// headers map
				contentType = headers.remove("Content-Type");
				for (String key : headers.keySet()) {
					con.setRequestProperty(key, headers.get(key));
				}
			}

			if ((bytes != null) && (bytes.length > 0)) {
				con.setDoOutput(true);
				if (contentType != null) {
					con.setRequestProperty("Content-Type", contentType);
				}

				OutputStream out = con.getOutputStream();
				out.write(bytes);
				out.flush();
				out.close();
			}
		}

		private Response retrieveResponse(HttpURLConnection con)
				throws Exception {

			Response response = new Response();

			response.setStatusCode(con.getResponseCode());
			response.setResponseHeaders(con.getHeaderFields());

			InputStream inputStream;
			try {
				inputStream = con.getInputStream();
			} catch (Exception e) {

				inputStream = con.getErrorStream();
				response.setFailure(e);
			}

			ByteArrayOutputStream container = new ByteArrayOutputStream();

			byte[] buf = new byte[1024];
			int read;
			while ((read = inputStream.read(buf, 0, 1024)) > 0) {
				container.write(buf, 0, read);
			}

			response.setResponseData(container.toByteArray());

			return response;
		}

		private SSLSocketFactory getSSLSocketFactory() throws Exception {
			try {
				SSLContext sslContext = SSLContexts.custom()
						.loadTrustMaterial(getKeyStore(), getTrustStrategy())
						.build();
				return sslContext.getSocketFactory();
			} catch (Exception e) {
				throw e;
			}
		}

		/**
		 * Provide your own keystore
		 * 
		 * @return KeyStore
		 */
		private KeyStore getKeyStore() {
			return null;
		}

		/**
		 * Provide your own TrustStrategy. Below method is set to trust all SSL
		 * by default
		 * 
		 * @return TrustStrategy
		 */
		private TrustStrategy getTrustStrategy() {
			return new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {
					return true;
				}
			};
		}
	}
}
