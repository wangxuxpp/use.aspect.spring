package use.aspect.common.invoke;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.ui.ModelMap;

import use.aspect.common.aspact.ReturnJsonAspect;
import use.aspect.interfacep.annotation.ApiDispatch;
import use.aspect.util.Util;
import use.common.exception.SystemException;
import use.common.json.JSONResult;
import use.common.spring.util.SpringContextUtils;
import use.common.util.ExceptionUtil;

public class InvokeHttpMap 
{
	protected final Logger log = LoggerFactory.getLogger(ReturnJsonAspect.class);
	
	public JSONResult invokeJson(HttpServletRequest request, HttpServletResponse response , ModelMap map)
	{
		JSONResult json = new JSONResult();
		ReInvokeObj r = null;
		try
		{
			r = this.invoke(request, response, map);
			if(r.ret != null)
			{
				json.setData(r.ret);
			}
			json.setSuccessType(r.ran.successMessage());
			json.setJsonType("success");
		}catch(Exception er)
		{
			String info = er.getMessage();
			
			try
			{
				if(r != null && r.ran != null)
				{
					info = r.ran.errorMessage()+er.getMessage();
				}
			}catch(Exception e){}
			
			json.setJsonType("error");
			json.setJsonMessage(Util.showAllErrorInfo ? info : info.substring(1, 30));
			
			ExceptionUtil.throwError(er, log);
		}
		return json;
	}
	
	public ReInvokeObj invoke(HttpServletRequest request, HttpServletResponse response , ModelMap map) throws NoSuchMethodException, SecurityException
	{
		String invokeObj = request.getParameter("object");
		invokeObj = invokeObj == null ? "" : invokeObj;
		if(invokeObj.equals(""))
		{
			throw new SystemException("请输入:object参数");
		}
		
		String invokeMethod = request.getParameter("method");
		invokeMethod = invokeMethod == null ? "" : invokeMethod;
		if(invokeMethod.equals(""))
		{
			throw new SystemException("请输入:method参数");
		}
		
		Object object = getObject(invokeObj);
		Method method = getMethod(object , invokeMethod);
		
		//spring中，getAnnotation获取不到annotation的，
		//原因是这里的bean其实是一个代理类。
		Method classMethod = AopUtils.getTargetClass(object).getMethod(invokeMethod, 
													HttpServletRequest.class,
													HttpServletResponse.class,
													ModelMap.class);
		
		ApiDispatch an = classMethod.getAnnotation(ApiDispatch.class);
		if(an == null)
		{
			throw new SystemException("method:"+invokeMethod+",需要ApiDispatch注解");
		}
		
		ReInvokeObj r = new ReInvokeObj();
		
		r.ran = an;
		try {
			r.ret = method.invoke(object, request , response, map);
			return r;
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	private class ReInvokeObj{
		public Object ret;
		public ApiDispatch  ran;
	}
	
	/**
	 * 通过spring 注入对象名称 得到对象
	 * @param objectName
	 * @return
	 */
	private Object getObject(final String objectName)
	{
		Object obj = null;
		try
		{
			obj = SpringContextUtils.getBeanById(objectName);
		}catch(Exception er)
		{
			throw new SystemException("请求object "+objectName+"不存在");
		}
		if(obj == null)
		{
			throw new SystemException("请求object "+objectName+"不存在");
		}
		return obj;
	}
	/**
	 * 获取参数调用方法
	 * @param obj
	 * @param methodName
	 * @return
	 */
	private Method getMethod(final Object obj , final String methodName)
	{
		Method  m = null;
		try
		{
			m = obj.getClass().getDeclaredMethod(methodName, HttpServletRequest.class , HttpServletResponse.class , ModelMap.class);
		}catch(Exception er)
		{
			throw new SystemException("请求method "+methodName+"不存在");
		}
		if(m == null)
		{
			throw new SystemException("请求method "+methodName+"不存在");
		}
		return m;
	}
}
