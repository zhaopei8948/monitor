package online.zhaopei.monitor.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import online.zhaopei.monitor.domain.Alert;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.Servers;

public final class CommonUtil {

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
	
	public static void generateSetting(List<Server> serverList) {
		try {
			JAXBContext context = JAXBContext.newInstance(Servers.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			Servers ss = new Servers(serverList);
			marshaller.marshal(ss, new File("servers.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
