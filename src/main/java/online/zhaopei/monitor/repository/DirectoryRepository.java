package online.zhaopei.monitor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.zhaopei.monitor.domain.Directory;

public interface DirectoryRepository extends JpaRepository<Directory, Long>, JpaSpecificationExecutor<Directory> {

}
