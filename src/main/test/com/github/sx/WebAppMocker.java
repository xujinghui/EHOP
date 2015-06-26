/**
 * 
 */
package com.github.sx;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author stevenxu
 *
 */
public class WebAppMocker {

	public static void main(String[] args) throws Exception {

		WebAppMocker.startup();
	}

	private static Server server = null;

	public static void startup() throws Exception {
		try {
			server = new Server(8080);

			WebAppContext context = new WebAppContext();
			context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
			context.setResourceBase("src/main/webapp");
			// context.setContextPath("/ehom");
			context.setContextPath("/");
			context.setParentLoaderPriority(true);

			server.setHandler(context);

			server.start();
			// server.join();

		} catch (Exception e) {
			shutdown();
			throw e;
		}
	}

	public static void shutdown() throws Exception {
		if (server != null) {
			server.stop();
		}
	}

}
