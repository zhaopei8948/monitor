package online.zhaopei.monitor.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "directory")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Directory implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4817816919356383751L;

	private Long id;
	
	private String path;
	
	private Integer countThreshold;
	
	private Boolean containSubdirectory;
	
	private Long serverId;
	
	private Server server;

	@XmlTransient
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getCountThreshold() {
		return countThreshold;
	}

	public void setCountThreshold(Integer countThreshold) {
		this.countThreshold = countThreshold;
	}

	public Boolean getContainSubdirectory() {
		return containSubdirectory;
	}

	public void setContainSubdirectory(Boolean containSubdirectory) {
		this.containSubdirectory = containSubdirectory;
	}

	@XmlTransient
	@Column(name = "server_id", insertable = false, updatable = false)
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	@XmlTransient
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "server_id")
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
