package es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.cxf;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.classloader.ClassLoaderUtils.ClassLoaderHolder;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.transport.servlet.AbstractHTTPServlet;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.apache.cxf.transport.servlet.ServletContextResourceResolver;
import org.apache.cxf.transport.servlet.ServletController;
import org.apache.cxf.transport.servlet.servicelist.ServiceListGeneratorServlet;


/**
 * Basada en implementacion Cxf 2.5.2 {@link org.apache.cxf.transport.servlet.CXFNonSpringServlet}
 * @author IECISA
 *
 */
public class CXFMultiEntityNonSpringServlet extends AbstractHTTPServlet {

    private DestinationRegistry destinationRegistry;
    private Bus bus;
    private ServletController controller;
    private ClassLoader loader;
    private boolean loadBus = true;
    
    public CXFMultiEntityNonSpringServlet() {
    }

    public CXFMultiEntityNonSpringServlet(DestinationRegistry destinationRegistry) {
        this(destinationRegistry, true);
    }
    public CXFMultiEntityNonSpringServlet(DestinationRegistry destinationRegistry,
                               boolean loadBus) {
        this.destinationRegistry = destinationRegistry;
        this.loadBus = loadBus;
    }

    @Override
    public void init(ServletConfig sc) throws ServletException {
        super.init(sc);
        if (this.bus == null && loadBus) {
            loadBus(sc);
        }
        if (this.bus != null) {
            loader = bus.getExtension(ClassLoader.class);
            ResourceManager resourceManager = bus.getExtension(ResourceManager.class);
            resourceManager.addResourceResolver(new ServletContextResourceResolver(
                                                   sc.getServletContext()));
            if (destinationRegistry == null) {
                this.destinationRegistry = getDestinationRegistryFromBus(this.bus);
            }
        }

        this.controller = createServletController(sc);
    }

    private static DestinationRegistry getDestinationRegistryFromBus(Bus bus) {
        DestinationFactoryManager dfm = bus.getExtension(DestinationFactoryManager.class);
        try {
            DestinationFactory df = dfm
                .getDestinationFactory("http://cxf.apache.org/transports/http/configuration");
            if (df instanceof HTTPTransportFactory) {
                HTTPTransportFactory transportFactory = (HTTPTransportFactory)df;
                return transportFactory.getRegistry();
            }
        } catch (BusException e) {
            // why are we throwing a busexception if the DF isn't found?
        }
        return null;
    }

    protected void loadBus(ServletConfig sc) {
        this.bus = BusFactory.newInstance().createBus();
        
    }

    protected ServletController createServletController(ServletConfig servletConfig) {
        HttpServlet serviceListGeneratorServlet = 
            new ServiceListGeneratorServlet(destinationRegistry, bus);
        ServletController newController =
            new ServletControllerCXFMultiEntity(destinationRegistry,
                                  servletConfig,
                                  serviceListGeneratorServlet);        
        return newController;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    protected void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        ClassLoaderHolder origLoader = null;
        try {
            if (loader != null) {
                origLoader = ClassLoaderUtils.setThreadContextClassloader(loader);
            }
            if (bus != null) {
                BusFactory.setThreadDefaultBus(bus);
            }
            controller.invoke(request, response);
        } finally {
            BusFactory.setThreadDefaultBus(null);
            if (origLoader != null) {
                origLoader.reset();
            }
        }
    }

    public void destroy() {
    	
        for (String path : destinationRegistry.getDestinationsPaths()) {
            // clean up the destination in case the destination itself can no longer access the registry later
            AbstractHTTPDestination dest = destinationRegistry.getDestinationForPath(path);
            synchronized (dest) {
                destinationRegistry.removeDestination(path);
                dest.releaseRegistry();
            }
        }
        destinationRegistry = null;
        destroyBus();
    }
    
    public void destroyBus() {
        if (bus != null) {
            bus.shutdown(true);
        }
    }
}
