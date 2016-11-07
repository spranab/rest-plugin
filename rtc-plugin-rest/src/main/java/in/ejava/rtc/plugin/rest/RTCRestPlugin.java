package in.ejava.rtc.plugin.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { RTCRestPlugin.class })
public class RTCRestPlugin {
	public static void main(String[] args) {
		SpringApplication.run(RTCRestPlugin.class, args);
	}
}
