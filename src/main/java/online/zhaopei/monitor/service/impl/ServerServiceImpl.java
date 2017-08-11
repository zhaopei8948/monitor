package online.zhaopei.monitor.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.repository.ServerRepository;
import online.zhaopei.monitor.service.ServerService;
import online.zhaopei.monitor.util.CommonUtil;

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

	@Override
	public Server findByIp(String ip) {
		return this.serverRepository.findByIp(ip);
	}

	@Override
	public void save(Iterable<Server> iterable) {
		this.serverRepository.save(iterable);
	}

	@Override
	public Long countByIp(String ip) {
		return this.serverRepository.countByIp(ip);
	}

	@Override
	public List<Server> findServerList(Server server) {
		return this.serverRepository.findAll(CommonUtil.findServerList(server));
	}

	@Override
	public Server getOne(Long id) {
		return this.serverRepository.getOne(id);
	}

	@Override
	public void delete(Long id) {
		this.serverRepository.delete(id);
	}

}
