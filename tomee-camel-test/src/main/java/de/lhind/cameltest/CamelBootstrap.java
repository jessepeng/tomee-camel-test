package de.lhind.cameltest;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;

/**
 * Created by U555413 on 23.11.2016.
 */
@Startup
@Singleton
public class CamelBootstrap {

    @Inject
    CamelContext camelCtx;

    @PostConstruct
    public void initialize() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connectionFactory.setTrustedPackages(new ArrayList<>(
                Arrays.asList("java.util,java.lang,java.math".split(","))));
        if (camelCtx.hasComponent("jms") == null) {
            JmsComponent jmsComponent =
                    JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
            camelCtx.addComponent("jms", jmsComponent);

            camelCtx.addRoutes(getInterceptionRouteBuilder());
        }
    }

    protected RouteInterceptionBuilder getInterceptionRouteBuilder() {
        return new RouteInterceptionBuilder(
                "jms://1",
                "jms://2");
    }

    @PreDestroy
    public void destroyed () throws Exception {
        camelCtx.stop();
    }
}
