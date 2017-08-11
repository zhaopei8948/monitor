package online.zhaopei.monitor.domain.test;

import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import online.zhaopei.monitor.domain.Alert;

public class AlertTest {

	@Test
	public void test() throws Exception {
		Alert alert = new Alert();
		alert.setHappenTime(Calendar.getInstance().getTime());
		alert.setHappenPosition("内存");
		alert.setDescription("超过80%的使用率");
		JAXBContext context = JAXBContext.newInstance(Alert.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(alert, System.out);
	}

}
