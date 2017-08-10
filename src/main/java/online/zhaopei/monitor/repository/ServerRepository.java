package online.zhaopei.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.zhaopei.monitor.domain.Server;

public interface ServerRepository extends JpaRepository<Server, Long> {

	Server findByIp(String ip);
	
	Long countByIp(String ip);
}
