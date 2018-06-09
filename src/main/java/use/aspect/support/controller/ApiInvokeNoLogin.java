package use.aspect.support.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import use.aspect.support.service.InvokeService;

@Controller
public class ApiInvokeNoLogin {

	@Resource(name="invokeService")
	private InvokeService fClass;
	
	@RequestMapping("/apinl.do")
	@ResponseBody
	public Object invoke(HttpServletRequest request, HttpServletResponse response, ModelMap map)
	{
		return fClass.invoke(request, response , map);
	}
}
