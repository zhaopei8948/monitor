package online.zhaopei.monitor.schedule;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import online.zhaopei.monitor.Application;
import online.zhaopei.monitor.domain.Alert;
import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.FileSystemInfo;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.service.ServerService;
import online.zhaopei.monitor.util.CommonUtil;
import online.zhaopei.monitor.util.HttpClientTool;

@Configuration
@EnableAsync
@EnableScheduling
public class ScheduledTaskConfig {

	private static Logger logger = Logger.getLogger(ScheduledTaskConfig.class);
	
	@Autowired
	private ServerService serverService;
	
	private static boolean IS_INIT = true;
	
	@Scheduled(initialDelay = 1000, fixedDelay = 300000)
	public void checkServers() throws Exception {
		logger.info("开始检查.........port=" + Application.SERVER_PORT);
		if (IS_INIT) {
			logger.info(HttpClientTool.get("http://localhost:" + Application.SERVER_PORT + "/findAll"));
			IS_INIT = false;
		}
		
		List<Server> serverList = this.serverService.findAll();
		String url = null;
		Mem mem = null;
		CpuPerc cpuPerc = null;
		List<FileSystemInfo> fileSystemInfoList = null;
		for (Server s : serverList) {
			url = "http://" + s.getIp() + ":" + s.getPort() + "/";
			try {
				mem = HttpClientTool.getMemJson(url);
				double usedPercent = mem.getUsedPercent();
				logger.info("memoryTHreshold=" + s.getMemoryThreshold() + "[]" + usedPercent);
				if (s.getMemoryThreshold() < usedPercent) {
					CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "内存", 
							"服务器[" + s.getIp() + "] 内存使用率超过[" + s.getMemoryThreshold() + "%]"));
				}
				
				cpuPerc = HttpClientTool.getCpuPercJson(url);
				double used = (1 - cpuPerc.getIdle()) * 100;
				logger.info("cpuThreshold=" + s.getCpuThreshold() + "[]" + used);
				if (s.getCpuThreshold() < used) {
					CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "CPU", 
							"服务器[" + s.getIp() + "] CPU使用率超过[" + s.getCpuThreshold() + "%]"));
				}
				
				fileSystemInfoList = HttpClientTool.getFileSystemInfoListJson(url);
				for (FileSystemInfo fsi : fileSystemInfoList) {
					double usePercent = fsi.getFileSystemUsage().getUsePercent() * 100;
					logger.info("hardDiskThreshold=" + s.getHardDiskThreshold() + "[]" + usePercent);
					if (s.getHardDiskThreshold() < usePercent) {
						CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "硬盘", 
								"服务器[" + s.getIp() + "] 硬盘[" + fsi.getFileSystem().getDevName() + "] 使用率超过[" + s.getHardDiskThreshold() + "%]"));
					}
				}
				
				for (Directory d : s.getDirectoryList()) {
					int fileCount = HttpClientTool.getDirectoryFileCount(url, d.getPath(), d.getContainSubdirectory());
					logger.info("countTHreshold=" + d.getCountThreshold() + "[]" + fileCount);
					if (d.getCountThreshold() < HttpClientTool.getDirectoryFileCount(url, d.getPath(), d.getContainSubdirectory())) {
						CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "目录", 
								"服务器[" + s.getIp() + "] 目录[" + d.getPath() + "] 下的文件(" + (d.getContainSubdirectory() ? "" : "不")
						+ "包含子目录)个数超过[" + d.getCountThreshold() + "]实际有[" + fileCount + "]"));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "服务", 
						"服务器[" + s.getIp() + "] 服务连接超时，请确认客户端已开启"));
			}
			
		}
	}
}
