package org.coderthoughts.servicejockey;

import java.util.HashMap;
import java.util.Map;

public class ProxyRule extends Rule {
    final Map<String, Object> addProperties = new HashMap<String, Object>(); // action

    public void addAddProperty(String key, String val) {
        addProperties.put(key, val);
    }

    public Map<String, Object> getAddProperties() {
        return addProperties;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(", filter=").append(serviceFilter);
        sb.append(", properties=").append(addProperties);
        return sb.toString();
    }
}
