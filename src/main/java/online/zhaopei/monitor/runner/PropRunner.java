package online.zhaopei.monitor.runner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.Servers;

@Component
public class PropRunner implements CommandLineRunner {
	
	public static List<Server> SERVER_LIST = new ArrayList<Server>();
	
	public static Boolean IS_SAVE = false;
	
	@Override
	public void run(String... arg0) {
		try {
			JAXBContext context = JAXBContext.newInstance(Servers.class);
			Unmarshaller um = context.createUnmarshaller();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("servers.xml"), "UTF-8"));
			Servers ss = (Servers) um.unmarshal(br);
			SERVER_LIST = ss.getServerList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
