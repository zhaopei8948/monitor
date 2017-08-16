package online.zhaopei.monitor.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import online.zhaopei.monitor.domain.Alert;
import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.Servers;
import online.zhaopei.monitor.service.ServerService;

public final class CommonUtil {

	private static Logger logger = Logger.getLogger(CommonUtil.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static void generateAlert(Alert alert) {
		try {
			JAXBContext context = JAXBContext.newInstance(Alert.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = "ALERT_" + sdf.format(Calendar.getInstance().getTime());
			File alertDir = new File("alert");
			if (!alertDir.exists()) {
				alertDir.mkdirs();
			}
			marshaller.marshal(alert, new File("alert/" + fileName + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Specification<Server> findServerList(final Server server) {
		return new Specification<Server>() {
			@Override
			public Predicate toPredicate(Root<Server> root, CriteriaQuery<?> criteriaquery,
					CriteriaBuilder criteriabuilder) {
				Predicate predicate = criteriabuilder.conjunction();
				
				if (null == server) {
					return predicate;
				}
				
				if (StringUtils.isNotEmpty(server.getIp())) {
					predicate.getExpressions().add(criteriabuilder.like(root.<String>get("ip"), server.getIp()));
				}
				
				if (null != server.getPort()) {
					predicate.getExpressions().add(criteriabuilder.equal(root.<String>get("port"), server.getPort()));
				}
				
				if (StringUtils.isNotEmpty(server.getDescription())) {
					predicate.getExpressions().add(criteriabuilder.like(root.<String>get("description"), "%" + server.getDescription() + "%"));
				}
				
				return predicate;
			}
		};
	}
	
	public static Specification<Directory> findDirectoryList(final Directory directory) {
		return new Specification<Directory>() {
			@Override
			public Predicate toPredicate(Root<Directory> root, CriteriaQuery<?> criteriaquery,
					CriteriaBuilder criteriabuilder) {
				Predicate predicate = criteriabuilder.conjunction();
				
				if (null == directory) {
					return predicate;
				}
				
				if (StringUtils.isNotEmpty(directory.getPath())) {
					predicate.getExpressions().add(criteriabuilder.like(root.<String>get("path"), directory.getPath()));
				}
				
				if (null != directory.getCountThreshold()) {
					predicate.getExpressions().add(criteriabuilder.equal(root.<String>get("count_threshold"), directory.getCountThreshold()));
				}
				
				if (null != directory.getContainSubdirectory()) {
					predicate.getExpressions().add(criteriabuilder.equal(root.<String>get("contain_subdirectory"), directory.getContainSubdirectory().toString().toUpperCase(Locale.ENGLISH)));
				}
				
				return predicate;
			}
		};
	}
	
	public static void generateSetting(ServerService serverService) {
		try {
			List<Server> serverList = serverService.findAll();
			logger.info("save--info=[" + mapper.writeValueAsString(serverList) + "]");
			generateSetting(serverList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void generateSetting(List<Server> serverList) {
		PrintWriter pw = null;
		try {
			JAXBContext context = JAXBContext.newInstance(Servers.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			Servers ss = new Servers(serverList);
			marshaller.marshal(ss, new File("servers.xml"));
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("servers.json")));
			pw.println(mapper.writeValueAsString(serverList));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != pw) {
				pw.close();
			}
		}
	}
}
