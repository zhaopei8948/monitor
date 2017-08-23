package online.zhaopei.monitor.schedule;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import online.zhaopei.monitor.Application;
import online.zhaopei.monitor.constant.CommonConstantEnum;
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
	
	private static Map<String, Integer> MAX_ALERT_MAP = null;
	
	@Value("${monitor.maxAlert}")
	private Integer maxAlert;
	
	@Scheduled(initialDelay = 1000, fixedDelay = 300000)
	public void checkServers() throws Exception {
		logger.info("开始检查.........port=" + Application.SERVER_PORT + "[]maxALert=" + maxAlert);
		if (IS_INIT) {
			logger.info(HttpClientTool.get("http://localhost:" + Application.SERVER_PORT + "/findAll"));
			IS_INIT = false;
		}
		
		if (null == MAX_ALERT_MAP) {
			MAX_ALERT_MAP = new HashMap<String, Integer>();
		}
		
		List<Server> serverList = this.serverService.findAll();
		String url = null;
		Mem mem = null;
		CpuPerc cpuPerc = null;
		String key = null;
		List<FileSystemInfo> fileSystemInfoList = null;
		for (Server s : serverList) {
			url = "http://" + s.getIp() + ":" + s.getPort() + "/";
			key = s.getIp() + "-" + CommonConstantEnum.ALERT_TIMEOUT.toString();
			if (null == MAX_ALERT_MAP.get(key)) {
				MAX_ALERT_MAP.put(key, this.maxAlert);
			}
			
			try {
				key = s.getIp() + "-" + CommonConstantEnum.ALERT_MEM.toString();
				if (null == MAX_ALERT_MAP.get(key)) {
					MAX_ALERT_MAP.put(key, this.maxAlert);
				}
				mem = HttpClientTool.getMemJson(url);
				double usedPercent = mem.getUsedPercent();
				logger.info("memoryThreshold=" + s.getMemoryThreshold() + "[]" + usedPercent);
				if (s.getMemoryThreshold() < usedPercent) {
					if (0 < MAX_ALERT_MAP.get(key)) {
						CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "内存", 
								"服务器[" + s.getIp() + "] 内存使用率超过[" + s.getMemoryThreshold() + "%]"));
						MAX_ALERT_MAP.put(key, MAX_ALERT_MAP.get(key) - 1);
					}
				} else {
					MAX_ALERT_MAP.put(key, this.maxAlert);
				}
				
				key = s.getIp() + "-" + CommonConstantEnum.ALERT_TIMEOUT.toString();
				MAX_ALERT_MAP.put(key, this.maxAlert);
				
				key = s.getIp() + "-" + CommonConstantEnum.ALERT_CPU.toString();
				if (null == MAX_ALERT_MAP.get(key)) {
					MAX_ALERT_MAP.put(key, this.maxAlert);
				}
				cpuPerc = HttpClientTool.getCpuPercJson(url);
				double used = (1 - cpuPerc.getIdle()) * 100;
				logger.info("cpuThreshold=" + s.getCpuThreshold() + "[]" + used);
				if (s.getCpuThreshold() < used) {
					if (0 < MAX_ALERT_MAP.get(key)) {
						CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "CPU", 
								"服务器[" + s.getIp() + "] CPU使用率超过[" + s.getCpuThreshold() + "%]"));
						MAX_ALERT_MAP.put(key, MAX_ALERT_MAP.get(key) - 1);
					}
				} else {
					MAX_ALERT_MAP.put(key, this.maxAlert);
				}
				
				fileSystemInfoList = HttpClientTool.getFileSystemInfoListJson(url);
				for (FileSystemInfo fsi : fileSystemInfoList) {
					String devName = fsi.getFileSystem().getDevName();
					key = s.getIp() + "-" + devName;
					if (null == MAX_ALERT_MAP.get(key)) {
						MAX_ALERT_MAP.put(key, this.maxAlert);
					}
					double usePercent = fsi.getFileSystemUsage().getUsePercent() * 100;
					logger.info("hardDiskThreshold=" + s.getHardDiskThreshold() + "[]" + usePercent);
					if (s.getHardDiskThreshold() < usePercent) {
						if (0 < MAX_ALERT_MAP.get(key)) {
							CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "硬盘", 
									"服务器[" + s.getIp() + "] 硬盘[" + devName + "] 使用率超过[" + s.getHardDiskThreshold() + "%]"));
							MAX_ALERT_MAP.put(key, MAX_ALERT_MAP.get(key) - 1);
						}
					} else {
						MAX_ALERT_MAP.put(key, this.maxAlert);
					}
				}
				
				for (Directory d : s.getDirectoryList()) {
					key = s.getIp() + "-" + d.getPath();
					if (null == MAX_ALERT_MAP.get(key)) {
						MAX_ALERT_MAP.put(key, this.maxAlert);
					}
					int fileCount = HttpClientTool.getDirectoryFileCount(url, d.getPath(), d.getContainSubdirectory());
					logger.info("countThreshold=" + d.getCountThreshold() + "[]" + fileCount);
					if (d.getCountThreshold() < HttpClientTool.getDirectoryFileCount(url, d.getPath(), d.getContainSubdirectory())) {
						if (0 < MAX_ALERT_MAP.get(key)) {
							CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "目录", 
									"服务器[" + s.getIp() + "] 目录[" + d.getPath() + "] 下的文件(" + (d.getContainSubdirectory() ? "" : "不")
							+ "包含子目录)个数超过[" + d.getCountThreshold() + "]实际有[" + fileCount + "]"));
							MAX_ALERT_MAP.put(key, MAX_ALERT_MAP.get(key) - 1);
						}
					} else {
						MAX_ALERT_MAP.put(key, this.maxAlert);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				key = s.getIp() + "-" + CommonConstantEnum.ALERT_TIMEOUT.toString();
				if (MAX_ALERT_MAP.get(key) > 0) {
					CommonUtil.generateAlert(new Alert(Calendar.getInstance().getTime(), s.getIp(), "服务", 
							"服务器[" + s.getIp() + "] 服务连接超时，请确认客户端已开启"));
					MAX_ALERT_MAP.put(key, MAX_ALERT_MAP.get(key) - 1);
				}
			}
			
		}
	}
}
