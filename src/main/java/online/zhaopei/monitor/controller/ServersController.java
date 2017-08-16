package online.zhaopei.monitor.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.domain.ServerInfo;
import online.zhaopei.monitor.service.DirectoryService;
import online.zhaopei.monitor.service.ServerService;
import online.zhaopei.monitor.util.CommonUtil;
import online.zhaopei.monitor.util.HttpClientTool;

@Controller
@RequestMapping("/")
public class ServersController {
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private DirectoryService directoryService;
	
	@RequestMapping
	public ModelAndView info(String ip, String port) {
		ModelAndView mv = new ModelAndView("index");
		List<Server> serverList = this.serverService.findAll();
		Server s = null;
		if (StringUtils.isEmpty(ip)) {
			if (null == serverList || serverList.isEmpty()) {
				return mv;
			} else {
				s = serverList.get(0);
				ip = s.getIp();
				port = s.getPort().toString();
			}
		} else {
			 s = this.serverService.findByIp(ip);
		}
		String httpPre = "http://";
		String url = null;
		ServerInfo serverInfo = null;
		try {
			url = httpPre + ip + ":" + port + "/";
			serverInfo = new ServerInfo();
			serverInfo.setServer(s);
			serverInfo.setMem(HttpClientTool.getMemJson(url));
			serverInfo.setSwap(HttpClientTool.getSwapJson(url));
			serverInfo.setCpuPerc(HttpClientTool.getCpuPercJson(url));
			serverInfo.setCpuList(HttpClientTool.getCpuListJson(url));
			serverInfo.setOperatingSystem(HttpClientTool.getOperatingSystemJson(url));
			serverInfo.setFileSystemInfoList(HttpClientTool.getFileSystemInfoListJson(url));
			serverInfo.setNetInterfaceInfoList(HttpClientTool.getNetInterfaceInfoListJson(url));
			if (null == serverInfo.getMem()) {
				serverInfo = null;
				throw new RuntimeException("调用" + url + "失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("serverInfo", serverInfo);
		mv.addObject("serverList", serverList);
		return mv;
	}
	
	@RequestMapping("/toSave")
	public ModelAndView toSave() {
		ModelAndView mv  = new ModelAndView("server/form");
		List<Server> serverList = this.serverService.findAll();
		mv.addObject("serverList", serverList);
		mv.addObject("server", new Server());
		return mv;
	}
	
	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable Long id) {
		ModelAndView mv  = new ModelAndView("server/form");
		List<Server> serverList = this.serverService.findAll();
		mv.addObject("serverList", serverList);
		mv.addObject("server", this.serverService.getOne(id));
		return mv;
	}
	
	@RequestMapping("/list")
	public ModelAndView list(Server server) {
		ModelAndView mv  = new ModelAndView("server/list");
		List<Server> serverList = this.serverService.findServerList(server);
		mv.addObject("serverList", serverList);
		return mv;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelAndView save(Server server) {
		Server s = this.serverService.save(server);
		CommonUtil.generateSetting(this.serverService.findAll());
		return new ModelAndView("redirect:/list");
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public ModelAndView save(@PathVariable Long id) {
		this.serverService.delete(id);
		CommonUtil.generateSetting(this.serverService.findAll());
		return new ModelAndView("redirect:/list");
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Server> findAll() {
		return this.serverService.findAll();
	}
}
