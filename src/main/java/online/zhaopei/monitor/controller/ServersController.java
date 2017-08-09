package online.zhaopei.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.service.ServerService;

@Controller
@RequestMapping("/servers")
public class ServersController {

	@Autowired
	private ServerService serverService;

	@RequestMapping
	public ModelAndView index() {
		return new ModelAndView("index");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Server save() {
		Server s = new Server();
		s.setIp("10.10.10.1");
		s.setPort(123);
		s.setMemoryThreshold(20);
		s.setCpuThreshold(10);
		s.setDescription("服务器1");
		
		this.serverService.save(s);
		
		return s;
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<Server> findAll() {
		return this.serverService.findAll();
	}
}
