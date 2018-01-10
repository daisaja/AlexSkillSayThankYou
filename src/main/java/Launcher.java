
import lars.LarsSpeechlet;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.amazon.speech.Sdk;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;

/**
 * Shared launcher for executing all sample skills within a single servlet container.
 */
public final class Launcher {
    private static final int PORT = 8080;

    //private static final String HTTPS_SCHEME = "https";
    private static final String HTTPS_SCHEME = "http";
    
    private Launcher() {
    }

    public static void main(final String[] args) throws Exception {
        // Configure logging to output to the console with default level of INFO
        BasicConfigurator.configure();

        // Configure server and its associated servlets
        Server server = new Server();

        HttpConfiguration httpConf = new HttpConfiguration();
        //httpConf.setSecurePort(PORT);
        //httpConf.setSecureScheme(HTTPS_SCHEME);
        //httpConf.addCustomizer(new SecureRequestCustomizer());
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConf);

        //ServerConnector serverConnector =
          //      new ServerConnector(server, sslConnectionFactory, httpConnectionFactory);
        ServerConnector serverConnector =
                new ServerConnector(server, httpConnectionFactory);
        serverConnector.setPort(PORT);

        Connector[] connectors = new Connector[1];
        connectors[0] = serverConnector;
        server.setConnectors(connectors);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        LarsSpeechlet larsSpeechlet = new LarsSpeechlet();
        
        context.addServlet(new ServletHolder(createServlet(larsSpeechlet)), "/larsSpeechlet");
        
        server.start();
        server.join();
    }


    private static SpeechletServlet createServlet(final Speechlet speechlet) {
        SpeechletServlet servlet = new SpeechletServlet();
        servlet.setSpeechlet(speechlet);
        return servlet;
    }
}
