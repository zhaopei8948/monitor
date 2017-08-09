package online.zhaopei.monitor.service;

import java.io.Serializable;
import java.util.List;

import online.zhaopei.monitor.domain.Server;

public interface ServerService extends Serializable {

	void save(Server server);
	
	List<Server> findAll();
}
