package com.oms.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Tomcat 서버 개인 설정 
 * @author user
 *
 */
@Configuration
public class TomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@SuppressWarnings("deprecation")
	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.addConnectorCustomizers((TomcatConnectorCustomizer)
				connector -> connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}"));
	}
	
}
