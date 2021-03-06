package online.zhaopei.monitor.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "servers")
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Servers implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4024556523905289490L;

	public Servers() {
		
	}
	
	public Servers(List<Server> serverList) {
		this.serverList = serverList;
	}
	
	private List<Server> serverList;

	@XmlElement(name = "server")
	public List<Server> getServerList() {
		return serverList;
	}

	public void setServerList(List<Server> serverList) {
		this.serverList = serverList;
	}
	
}
