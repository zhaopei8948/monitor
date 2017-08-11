package online.zhaopei.monitor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements 
	ApplicationListener<EmbeddedServletContainerInitializedEvent>{

	public static int SERVER_PORT;
	
	public static String SERVER_ADDRESS;
	
	private static Logger logger = Logger.getLogger(Application.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		SERVER_PORT =  event.getEmbeddedServletContainer().getPort();
		logger.info("获取服务端口号....." + SERVER_PORT);
		try {
			SERVER_ADDRESS = InetAddress.getLocalHost().getHostAddress();
			logger.info("获取服务器ip地址...." + SERVER_ADDRESS);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
