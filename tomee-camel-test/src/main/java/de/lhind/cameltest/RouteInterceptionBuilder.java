package de.lhind.cameltest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U555413 on 23.11.2016.
 */
public class RouteInterceptionBuilder extends RouteBuilder {

    private List<RouteInterceptor> interceptors;
    private String interceptionUri;
    private String endpointUri;

    public RouteInterceptionBuilder(String anInterceptionUri, String anEndpointUri) {
        interceptionUri = anInterceptionUri;
        endpointUri = anEndpointUri;
    }

    @Override
    public void configure() throws Exception {
        onException(Throwable.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        for (RouteInterceptor routeInterceptor : interceptors) {
                            routeInterceptor.onException(exchange);
                        }
                    }
                });

        from(interceptionUri)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        for (RouteInterceptor routeInterceptor : interceptors) {
                            routeInterceptor.beforeEndpoint(exchange);
                        }
                    }
                })
                .to(endpointUri)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        for (RouteInterceptor routeInterceptor : interceptors) {
                            routeInterceptor.afterEndpoint(exchange);
                        }
                    }
                });

    }

    public void addInterceptor(RouteInterceptor interceptor) {
        if (interceptors == null) {
            interceptors = new ArrayList<RouteInterceptor>();
        }
        interceptors.add(interceptor);
    }
}
