/**
 * 
 */
package com.github.sx.hcm.mvc.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author stevenxu
 *
 */

public class HelloWorldController implements
		org.springframework.web.servlet.mvc.Controller {
	private final Logger logger = LoggerFactory
			.getLogger(HelloWorldController.class);

	// public String greeting(
	// @RequestParam(value = "name", required = false, defaultValue = "World")
	// String name,
	// Model model) {
	// logger.info("HelloWorldController 1");
	// model.addAttribute("name", name);
	// logger.info("HelloWorldController 2");
	// return "greeting";
	// }

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView model = new ModelAndView();
		logger.info("HelloWorldController 1");
		model.addObject("name", "Hello");
		logger.info("HelloWorldController 2");
		model.setViewName("hello");
		model.getModel().put("name", "World");
		return model;
	}

}
