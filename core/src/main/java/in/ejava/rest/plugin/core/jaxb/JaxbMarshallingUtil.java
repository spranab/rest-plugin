package in.ejava.rest.plugin.core.jaxb;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * A utility class for converting between jaxb annotated objects and xml.
 */
public class JaxbMarshallingUtil {

	private JaxbMarshallingUtil() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T marshal(Class<T> c, String xml) throws JAXBException {
		T res;

		if (c == xml.getClass()) {
			res = (T) xml;
		}

		else {
			JAXBContext ctx = JAXBContext.newInstance(c);
			Unmarshaller marshaller = ctx.createUnmarshaller();
			res = (T) marshaller.unmarshal(new StringReader(xml));
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	public static <T> String unmarshal(Class<T> c, Object o) throws Exception {

		JAXBContext ctx = JAXBContext.newInstance(c);
		Marshaller marshaller = ctx.createMarshaller();
		StringWriter entityXml = new StringWriter();
		marshaller.marshal(o, entityXml);

		String entityString = entityXml.toString();

		return entityString;
	}
}