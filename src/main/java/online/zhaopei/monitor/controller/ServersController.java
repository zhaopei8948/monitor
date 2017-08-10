package online.zhaopei.monitor.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.ServerInfo;
import online.zhaopei.monitor.domain.Servers;
import online.zhaopei.monitor.runner.PropRunner;
import online.zhaopei.monitor.service.ServerService;
import online.zhaopei.monitor.util.HttpClientTool;

@Controller
@RequestMapping("/")
public class ServersController {
	
	@Autowired
	private ServerService serverService;

	private void initProp() {
		if (!PropRunner.IS_SAVE) {
			this.serverService.save(PropRunner.SERVER_LIST);
			PropRunner.IS_SAVE = true;
		}
	}
	
	@RequestMapping
	public ModelAndView info(String ip, String port) {
		this.initProp();
		ModelAndView mv = new ModelAndView("index");
		List<Server> serverList = this.serverService.findAll();
		Server s = null;
		if (StringUtils.isEmpty(ip)) {
			if (null == serverList || serverList.isEmpty()) {
				return mv;
			} else {
				s = serverList.get(0);
				ip = s.getIp();
			}
		} else {
			 s = this.serverService.findByIp(ip);
		}
		String httpPre = "http://";
		String url = null;
		ServerInfo serverInfo = null;
//		try {
//			url = httpPre + ip + ":" + port + "/";
//			serverInfo = new ServerInfo();
//			serverInfo.setServer(s);
//			serverInfo.setMem(HttpClientTool.getMemJson(url));
//			serverInfo.setSwap(HttpClientTool.getSwapJson(url));
//			serverInfo.setCpuPerc(HttpClientTool.getCpuPercJson(url));
//			serverInfo.setCpuList(HttpClientTool.getCpuListJson(url));
//			serverInfo.setOperatingSystem(HttpClientTool.getOperatingSystemJson(url));
//			serverInfo.setFileSystemInfoList(HttpClientTool.getFileSystemInfoListJson(url));
//			serverInfo.setNetInterfaceInfoList(HttpClientTool.getNetInterfaceInfoListJson(url));
//			if (null == serverInfo.getMem()) {
//				serverInfo = null;
//				throw new RuntimeException("调用" + url + "失败!");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		mv.addObject("serverInfo", serverInfo);
		mv.addObject("serverList", serverList);
		return mv;
	}
	
	@RequestMapping("/toSave")
	public ModelAndView toSave() {
		ModelAndView mv  = new ModelAndView("form");
		List<Server> serverList = this.serverService.findAll();
		mv.addObject("serverList", serverList);
		return mv;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelAndView save(Server server) {
		this.serverService.save(server);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Server> findAll() {
		return this.serverService.findAll();
	}
}
