package online.zhaopei.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.zhaopei.monitor.domain.Server;

public interface ServerRepository extends JpaRepository<Server, Long>, JpaSpecificationExecutor<Server> {

	Server findByIp(String ip);
	
	Long countByIp(String ip);
}
