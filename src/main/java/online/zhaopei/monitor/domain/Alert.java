package online.zhaopei.monitor.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import online.zhaopei.monitor.adapter.DateAdapter;

@XmlRootElement(name = "alert")
@XmlAccessorType(XmlAccessType.FIELD)
public class Alert implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -170429854800463172L;

	public Alert() {
		
	}
	
	public Alert(Date happenTime, String ip, String happenPosition, String description) {
		this.ip = ip;
		this.happenPosition = happenPosition;
		this.happenTime = happenTime;
		this.description = description;
	}
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date happenTime;
	
	private String ip;
	
	private String happenPosition;
	
	private String description;

	public Date getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHappenPosition() {
		return happenPosition;
	}

	public void setHappenPosition(String happenPosition) {
		this.happenPosition = happenPosition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
