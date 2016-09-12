package com.ps.rest.plugin.core.client;

import org.springframework.web.client.RestTemplate;

public class RestTemplateFactory {
	private RestTemplate restTemplate;
	private static RestTemplateFactory instance = new RestTemplateFactory();

	static {
		RestTemplate restTemplate = new RestTemplate();
	}

	/**
	 * Private constructor to make the factory class as singleton
	 */
	private RestTemplateFactory() {
	}

	/**
	 * To get the instance for RestTemplateFactory
	 * 
	 * @return RestTemplateFactory
	 */
	public static RestTemplateFactory getInstance() {
		return instance;
	}

}
