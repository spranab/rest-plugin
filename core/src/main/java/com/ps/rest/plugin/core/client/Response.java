package com.ps.rest.plugin.core.client;

import java.util.Map;

public class Response {
	private Map<String, ? extends Iterable<String>> responseHeaders = null;
	private byte[] responseData = null;
	private Exception failure = null;
	private int statusCode = 0;

	public Response(Map<String, Iterable<String>> responseHeaders,
			byte[] responseData, Exception failure, int statusCode) {
		super();
		this.responseHeaders = responseHeaders;
		this.responseData = responseData;
		this.failure = failure;
		this.statusCode = statusCode;
	}

	public Response() {
	}

	/**
	 * @return responseHeaders
	 */
	public Map<String, ? extends Iterable<String>> getResponseHeaders() {
		return responseHeaders;
	}

	/**
	 * @param responseHeaders
	 */
	public void setResponseHeaders(
			Map<String, ? extends Iterable<String>> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}

	/**
	 * @return responseData
	 */
	public byte[] getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData
	 */
	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}

	/**
	 * @return failure information
	 */
	public Exception getFailure() {
		return failure;
	}

	/**
	 * @param failure
	 */
	public void setFailure(Exception failure) {
		this.failure = failure;
	}

	/**
	 * @return statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return response in String format
	 */
	@Override
	public String toString() {

		return new String(this.responseData);
	}
}
