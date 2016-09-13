package in.ejava.rest.plugin.core;

import in.ejava.rest.plugin.core.client.Response;
import in.ejava.rest.plugin.core.client.RestClientFactory;
import in.ejava.rest.plugin.core.jaxb.JaxbMarshallingUtil;

import java.util.Map;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseClient {
	private String sessionIdentifier;
	private String baseUrl;

	public String getSessionIdentifier() {
		return sessionIdentifier;
	}

	public void setSessionIdentifier(String sessionIdentifier) {
		this.sessionIdentifier = sessionIdentifier;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public BaseClient(String sessionIdentifier, String baseUrl) {
		this.sessionIdentifier = sessionIdentifier;
		this.baseUrl = baseUrl;
	}

	public Object makeCall(String url, HttpMethod httpMethod,
			Map<String, String> headers, String queryString, String payload,
			DataType responseDataType, Class typeReference) throws Exception {
		Response response = getResponse(url, httpMethod, headers, queryString,
				payload);

		switch (responseDataType) {
		case JSON:
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(response.toString(), typeReference);
		case XML:
			return JaxbMarshallingUtil.marshal(typeReference,
					response.toString());
		case STRING:
			return response.toString();
		default:
			break;
		}
		return response;
	}

	public Object makeCall(String url, HttpMethod httpMethod,
			Map<String, String> headers, String queryString, String payload,
			DataType responseDataType, TypeReference typeReference)
			throws Exception {
		Response response = getResponse(url, httpMethod, headers, queryString,
				payload);

		switch (responseDataType) {
		case JSON:
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(response.toString(), typeReference);
		case XML:
			throw new Exception("TypeReference Unsupported for DataType.XML");
		case STRING:
			return response.toString();
		default:
			break;
		}
		return response;
	}

	private Response getResponse(String url, HttpMethod httpMethod,
			Map<String, String> headers, String queryString, String payload)
			throws Exception {
		Response response = null;
		switch (httpMethod) {
		case GET:
			response = RestClientFactory.getInstance().getRestClient()
					.get(url, queryString, headers, sessionIdentifier);
			break;
		case POST:
			response = RestClientFactory.getInstance().getRestClient()
					.post(url, payload.getBytes(), headers, sessionIdentifier);
			break;
		case PUT:
			response = RestClientFactory.getInstance().getRestClient()
					.put(url, payload.getBytes(), headers, sessionIdentifier);
			break;
		case DELETE:
			response = RestClientFactory.getInstance().getRestClient()
					.delete(url, headers, sessionIdentifier);
			break;

		default:
			break;
		}
		return response;
	}

	public String buildUrl(String path) {
		return String.format("%1$s/%2$s", baseUrl, path);
	}

}
