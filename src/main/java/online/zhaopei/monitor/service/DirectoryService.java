package online.zhaopei.monitor.service;

import java.io.Serializable;
import java.util.List;

import online.zhaopei.monitor.domain.Directory;

public interface DirectoryService extends Serializable {

	Directory save(Directory directory);
	
	void save(Iterable<Directory> iterable);
	
	List<Directory> findDirectoryList(Directory directory);
	
	Directory getOne(Long id);
	
	void delete(Long id);
}
