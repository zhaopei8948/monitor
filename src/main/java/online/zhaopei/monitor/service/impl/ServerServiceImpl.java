package online.zhaopei.monitor.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.repository.ServerRepository;
import online.zhaopei.monitor.service.ServerService;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -247042531865508784L;

	@Autowired
	private ServerRepository serverRepository;
	
	@Override
	public void save(Server server) {
		this.serverRepository.save(server);
	}

	@Override
	public List<Server> findAll() {
		return this.serverRepository.findAll();
	}

}
