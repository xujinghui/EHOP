/**
 * 
 */
package com.github.sx.hcm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author stevenxu
 *
 */
public class DerbyDriver {

	static {
		System.out.println(System.getenv("PATH"));
		System.out.println(System.setProperty("derby.system.home",
				"D:\\Steven\\DerbyDBHome"));
		System.out.println(System.getenv("derby.system.home"));
		System.out.println(System.getProperty("derby.system.home"));
		//
		// try {
		// Runtime.getRuntime().exec("cmd ech %PATH%");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfig.class);
//		TransferService transferService = ctx.getBean(TransferService.class);
	}
}
