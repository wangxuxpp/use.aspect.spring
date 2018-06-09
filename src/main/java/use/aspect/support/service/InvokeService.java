package use.aspect.support.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import use.aspect.common.invoke.InvokeHttpMap;

@Service("invokeService")
public class InvokeService 
{

	private InvokeHttpMap Invoke = new InvokeHttpMap();
	
	public Object invoke(HttpServletRequest request, HttpServletResponse response , ModelMap map) 
	{
		return Invoke.invokeJson(request, response, map);
	}

}
