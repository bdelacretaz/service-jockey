package org.coderthoughts.servicejockey;

public class RestrictRule extends Rule {
    String extraFilter;

    public String getExtraFilter() {
        return extraFilter;
    }

    public void setExtraFilter(String val) {
        extraFilter = val;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(", filter=").append(serviceFilter);
        sb.append(", extraFilter=").append(extraFilter);
        return sb.toString();
    }
}
