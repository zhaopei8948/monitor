package online.zhaopei.monitor.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "server_list")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	private List<Directory> directoryList;

	@XmlTransient
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
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

	@XmlElement(name = "directory")
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "server", cascade = CascadeType.REMOVE)
	public List<Directory> getDirectoryList() {
		return directoryList;
	}

	public void setDirectoryList(List<Directory> directoryList) {
		this.directoryList = directoryList;
	}
}
