package online.zhaopei.monitor.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.zhaopei.monitor.domain.Directory;
import online.zhaopei.monitor.domain.Server;
import online.zhaopei.monitor.runner.PropRunner;
import online.zhaopei.monitor.service.DirectoryService;
import online.zhaopei.monitor.service.ServerService;

@Aspect
@Component
public class ControllerInterceptor {

	private static Logger logger = Logger.getLogger(ControllerInterceptor.class);
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private DirectoryService directoryService;
	
	@Pointcut("execution(* online.zhaopei.monitor.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void controllerMethodPointcut() {}
	
	@Around("controllerMethodPointcut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("初始化配置文件============" + pjp);
		Server ts = null;
		if (!PropRunner.IS_SAVE) {
			if (null != PropRunner.SERVER_LIST) {
				for (Server s : PropRunner.SERVER_LIST) {
					ts = this.serverService.save(s);
					if (null != s.getDirectoryList()) {
						for (Directory d : s.getDirectoryList()) {
							ts.setDirectoryList(null);
							d.setServer(ts);
							this.directoryService.save(d);
						}
					}
				}
			}
			PropRunner.IS_SAVE = true;
		}
		Object obj = pjp.proceed();
		return obj;
	}
}
