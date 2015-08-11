package org.coderthoughts.servicejockey;

import java.net.URL;
import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.util.tracker.BundleTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceJockeyListener implements ServiceListener {
    final ServiceHandlerCatalog shc;
    BundleTracker bt;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public ServiceJockeyListener(BundleContext context, ServiceHandlerCatalog catalog) {
        shc = catalog;
        
        bt = new BundleTracker(context, Bundle.STARTING | Bundle.ACTIVE, null) {
            @Override
            @SuppressWarnings("unchecked")
            public Object addingBundle(Bundle bundle, BundleEvent event) {
                Dictionary props = bundle.getHeaders();
                Object header = props.get("Service-Jockey");
                if (header != null) {
                    try {
                        final String path = "/" + header;
                        final URL definition = bundle.getEntry(path);
                        if(definition == null) {
                            log.warn("ServiceJockey definition not found: {} for bundle {}", path, bundle.getSymbolicName());
                        } else {
                            shc.addDefinition(definition);
                        }
                    } catch (Exception e) {
                        log.error("Error while adding rules from bundle " + bundle.getSymbolicName(), e);
                    }
                }
                return super.addingBundle(bundle, event);
            }            
        };
        bt.open();
    }
    
    public void close() {
        bt.close();
    }

    public void serviceChanged(ServiceEvent event) {
        try {
            switch (event.getType()) {
            case ServiceEvent.REGISTERED:
                shc.serviceRegistered(event.getServiceReference());
                break;
            case ServiceEvent.UNREGISTERING:
                shc.serviceUnregistering(event.getServiceReference());
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
