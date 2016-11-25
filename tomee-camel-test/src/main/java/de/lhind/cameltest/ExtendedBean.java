package de.lhind.cameltest;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

@ManagedBean("ExtendedBean")
@ApplicationScoped
public class ExtendedBean {

	public String getHelloWorld() {
		return "Hello extended World!";
	}
	
}
