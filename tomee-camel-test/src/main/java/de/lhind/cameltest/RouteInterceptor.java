package de.lhind.cameltest;

import org.apache.camel.Exchange;

/**
 * Created by U555413 on 23.11.2016.
 */
public interface RouteInterceptor {


    public void beforeEndpoint(Exchange exchange);

    public void afterEndpoint(Exchange exchange);

    public void onException(Exchange exchange);
}
