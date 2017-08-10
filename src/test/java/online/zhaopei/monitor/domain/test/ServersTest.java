package online.zhaopei.monitor.domain.test;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.Servers;

public class ServersTest {

	@Test
	public void test() throws Exception {
		Servers ss = new Servers();
		Server s = null;
		List<Server> serverList = new ArrayList<Server>();
		for (int i = 0; i < 3; i++) {
			s = new Server();
			s.setId(Long.valueOf(i));
			s.setIp("10.10.10." + i);
			s.setPort(20 + i);
			s.setDescription("server" + i);
			s.setCpuThreshold(10 + i);
			s.setMemoryThreshold(40 + i);
			s.setHardDiskThreshold(30 + i);
			serverList.add(s);
		}
		ss.setServerList(serverList);
		
		JAXBContext context = JAXBContext.newInstance(Servers.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.marshal(ss, System.out);
	}

}
