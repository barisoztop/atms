package de.tum.in.dbpra;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
	final static String WEB_APP_DIR = "src/main/webapp/";

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.addConnector(connector);
		
		WebAppContext rootContext = new WebAppContext();
		rootContext.setContextPath("/");
		rootContext.setDescriptor(WEB_APP_DIR + "/WEB-INF/web.xml");
        rootContext.setResourceBase(WEB_APP_DIR);
        rootContext.setParentLoaderPriority(true);

		server.setHandler(rootContext);
		server.start();
		server.join(); 
	}
}