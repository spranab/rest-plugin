package in.ejava.rest.plugin.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PluginProperties {
	private static Properties properties = new Properties();
	static {
		try {
			System.out.println("Started loading property files...");
			String basePath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath();
			System.out.println("Looking for property files from base path: "
					+ basePath);
			final File folder = new File(basePath);
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.getName().endsWith(".properties")) {
					Properties temp = new Properties();
					temp.load(new FileInputStream(fileEntry));
					properties.putAll(temp);
					System.out.println("Loaded property file: "
							+ fileEntry.getPath() + "/" + fileEntry.getName());
				}
			}
			System.out.println("Completed loading property files...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPropery(String propertyName) {
		if (properties.containsKey(propertyName)) {
			return properties.getProperty(propertyName);
		}
		return null;
	}
}
