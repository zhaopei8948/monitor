package online.zhaopei.monitor.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "server_list")
public class Server implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8119728299119023931L;

	private Long id;
	
	private String ip;
	
	private Integer port;

	private String description;
	
	private Integer cpuThreshold;
	
	private Integer memoryThreshold;
	
	private Integer hardDiskThreshold;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCpuThreshold() {
		return cpuThreshold;
	}

	public void setCpuThreshold(Integer cpuThreshold) {
		this.cpuThreshold = cpuThreshold;
	}

	public Integer getMemoryThreshold() {
		return memoryThreshold;
	}

	public void setMemoryThreshold(Integer memoryThreshold) {
		this.memoryThreshold = memoryThreshold;
	}

	public Integer getHardDiskThreshold() {
		return hardDiskThreshold;
	}

	public void setHardDiskThreshold(Integer hardDiskThreshold) {
		this.hardDiskThreshold = hardDiskThreshold;
	}
}
