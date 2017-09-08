package online.zhaopei.monitor.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.service.DirectoryService;
import online.zhaopei.monitor.service.ServerService;
import online.zhaopei.monitor.util.CommonUtil;

@Controller
@RequestMapping("/directories")
public class DirectoriesController {

	@Autowired
	private DirectoryService directoryService;
	
	@Autowired
	private ServerService serverService;
	
	@RequestMapping("/list")
	public ModelAndView list(Directory directory) {
		ModelAndView mv  = new ModelAndView("directory/list");
		List<Directory> directoryList = this.directoryService.findDirectoryList(directory);
		mv.addObject("serverList", this.serverService.findAll());
		mv.addObject("directoryList", directoryList);
		return mv;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelAndView save(Directory directory) {
		directory.setServer(this.serverService.getOne(directory.getServerId()));
		this.directoryService.save(directory);
		CommonUtil.generateSetting(this.serverService);
		return new ModelAndView("redirect:/directories/list");
	}
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public ModelAndView delete(@PathVariable Long id) {
		this.directoryService.delete(id);
		CommonUtil.generateSetting(this.serverService);
		return new ModelAndView("redirect:/directories/list");
	}
	
	@RequestMapping("/toSave")
	public ModelAndView toSave() {
		ModelAndView mv  = new ModelAndView("directory/form");
		List<Server> serverList = this.serverService.findAll();
		HashMap<Long, String> serverMap = new HashMap<Long, String>();
		for (Server s : serverList) {
			serverMap.put(s.getId(), s.getIp() + ":" + s.getDescription());
		}
			
		mv.addObject("serverList", serverList);
		mv.addObject("directory", new Directory());
		mv.addObject("serverMap", serverMap);
		return mv;
	}
	
	@RequestMapping("/toUpdate/{id}")
	public ModelAndView toUpdate(@PathVariable Long id) {
		ModelAndView mv  = new ModelAndView("directory/form");
		List<Server> serverList = this.serverService.findAll();
		HashMap<Long, String> serverMap = new HashMap<Long, String>();
		for (Server s : serverList) {
			serverMap.put(s.getId(), s.getIp() + ":" + s.getDescription());
		}
		mv.addObject("serverList", this.serverService.findAll());
		mv.addObject("server", this.serverService.getOne(id));
		mv.addObject("serverMap", serverMap);
		mv.addObject("directory", this.directoryService.getOne(id));
		return mv;
	}
}
