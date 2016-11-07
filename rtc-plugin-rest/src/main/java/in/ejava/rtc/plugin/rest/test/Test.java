package in.ejava.rtc.plugin.rest.test;

import in.ejava.rtc.plugin.rest.api.AuthenticationAPI;
import in.ejava.rtc.plugin.rest.api.ProjectAPI;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@RequestMapping(value = "/authenticate/{username}/{password}")
	@ResponseBody
	public String authenticate(@PathVariable String username,
			@PathVariable String password) {
		AuthenticationAPI authenticationAPI = new AuthenticationAPI(username);
		authenticationAPI.authenticate(password);

		ProjectAPI projectAPI = new ProjectAPI(username);
		return projectAPI.getProjects();
		// return "test";
	}
}
