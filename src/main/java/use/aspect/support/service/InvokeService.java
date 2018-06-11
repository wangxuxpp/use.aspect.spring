package use.aspect.support.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import use.aspect.common.invoke.InvokeHttpMap;

public class InvokeService 
{
	private static InvokeService  fthis = new InvokeService();
	public static InvokeService get()
	{
		return fthis;
	}
	
	private InvokeService()
	{
	}
	private InvokeHttpMap Invoke = new InvokeHttpMap();
	
	public Object invoke(HttpServletRequest request, HttpServletResponse response , ModelMap map) 
	{
		return Invoke.invokeJson(request, response, map);
	}

}
