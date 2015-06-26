/**
 * 
 */
package com.github.sx.hcm.mvc.dc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author stevenxu
 *
 */
@Controller
public class DataCapacityPerDay {
    private final Logger logger = LoggerFactory.getLogger(DataCapacityPerDay.class);

    @RequestMapping("/dcpd")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name,
	    Model model) {

	model.addAttribute("name", name);

	logger.info("ThymeleafGreeting 1");
	return "dn_usage";
	// return "classpath:templates/greeting";
    }
}
