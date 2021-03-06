package use.aspect.support.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import use.aspect.support.service.InvokeService;

@Controller
public class ApiInvoke {

	@RequestMapping("/api.do")
	@ResponseBody
	public Object invoke(HttpServletRequest request, HttpServletResponse response, ModelMap map)
	{
		return InvokeService.get().invoke(request, response , map);
	}
}
