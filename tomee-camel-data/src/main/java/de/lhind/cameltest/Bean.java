package de.lhind.cameltest;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean("Bean")
@ApplicationScoped
public class Bean {

	public String getHelloWorld() {
		return "Hello, team!";
	}
	
}
