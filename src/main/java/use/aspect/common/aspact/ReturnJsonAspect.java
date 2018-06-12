package use.aspect.common.aspact;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import use.aspect.interfacep.annotation.ReturnJson;
import use.aspect.interfacep.interf.IAspectInterface;
import use.aspect.util.Util;
import use.common.exception.JsonException;
import use.common.exception.SystemException;
import use.common.json.JSONResult;
/**
 * json返回切面
 * 项目名称:use.aspect.spring
 * 类型名称:ReturnJsonAspect
 * 类型描述:
 * 作者:wx
 * 创建时间:2018年6月8日
 * @version:
 */
@Aspect
@Component
public class ReturnJsonAspect implements IAspectInterface{

	protected final Logger log = LoggerFactory.getLogger(ReturnJsonAspect.class);
	
	@Pointcut(value="@annotation(use.aspect.interfacep.annotation.ReturnJson)")
	public void returnJson() {
	} 

	
	@Around(value="@annotation(an)")
	public JSONResult returnJsonInvoke(ProceedingJoinPoint jp , ReturnJson an)
	{
		JSONResult json = new JSONResult();
		try
		{
			Object r = null;
			if(jp.getArgs().length >0)
			{
				r = jp.proceed(jp.getArgs());
			}else {
				r = jp.proceed();
			}
			if(r != null)
			{
				json.setData(r);
			}
			json.setJsonMessage(an.successMessage());
			json.setJsonType("success");
			return json;
		}catch(Exception er)
		{
			String info = an.errorMessage()+er.getMessage();
			json.setJsonType("error");
			json.setJsonMessage(Util.showAllErrorInfo ? info : info.substring(1, 30));
			JsonException.threw(er, json);
			throw new SystemException(er);
		} catch (Throwable e) {
			String info = an.errorMessage()+e.getMessage();
			json.setJsonType("error");
			json.setJsonMessage(Util.showAllErrorInfo ? info : info.substring(1, 30));
			JsonException.threw(e, json);
			throw new SystemException(e);
		}
	}
}
