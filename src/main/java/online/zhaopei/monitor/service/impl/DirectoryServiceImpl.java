package online.zhaopei.monitor.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.repository.DirectoryRepository;
import online.zhaopei.monitor.service.DirectoryService;
import online.zhaopei.monitor.util.CommonUtil;

@Service
@Transactional
public class DirectoryServiceImpl implements DirectoryService {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 153943973048898031L;

	@Autowired
	private DirectoryRepository directoryRepository;
	
	@Override
	public void save(Iterable<Directory> iterable) {
		this.directoryRepository.save(iterable);
	}

	@Override
	public Directory save(Directory directory) {
		return this.directoryRepository.save(directory);
	}

	@Override
	public List<Directory> findDirectoryList(Directory directory) {
		return this.directoryRepository.findAll(CommonUtil.findDirectoryList(directory));
	}

	@Override
	public void delete(Long id) {
		this.directoryRepository.delete(id);
	}

	@Override
	public Directory getOne(Long id) {
		return this.directoryRepository.getOne(id);
	}

}
