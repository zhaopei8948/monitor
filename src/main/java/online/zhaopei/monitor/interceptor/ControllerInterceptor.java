package online.zhaopei.monitor.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import online.zhaopei.monitor.runner.PropRunner;
import online.zhaopei.monitor.service.ServerService;

@Aspect
@Component
public class ControllerInterceptor {

	private static Logger logger = Logger.getLogger(ControllerInterceptor.class);
	
	@Autowired
	private ServerService serverService;
	
	@Pointcut("execution(* online.zhaopei.monitor.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void controllerMethodPointcut() {}
	
	@Around("controllerMethodPointcut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("初始化配置文件============" + pjp);
		if (!PropRunner.IS_SAVE) {
			this.serverService.save(PropRunner.SERVER_LIST);
			PropRunner.IS_SAVE = true;
		}
		Object obj = pjp.proceed();
		return obj;
	}
}
