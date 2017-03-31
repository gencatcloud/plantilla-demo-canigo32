package cat.gencat.plantillacanigo;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;
import org.webjars.servlet.WebjarsServlet;

import cat.gencat.ctti.canigo.arch.web.core.filters.LoggingFilter;
import cat.gencat.plantillacanigo.config.ConfigConstant;

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(final String[] args) throws Exception {
		if (System.getProperty("entorn") == null) {
			System.setProperty("entorn", "loc");
		}

		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	@Bean
    public WebjarsServlet webjarsServlet() {
		return new WebjarsServlet();
    }

    @Bean
    public ServletRegistrationBean webjarsServletRegistration() {
        final ServletRegistrationBean registration = new ServletRegistrationBean(webjarsServlet(), "/webjars/*");
        registration.setName("webjarsServletRegistration");
        registration.setLoadOnStartup(2);
        return registration;
    }

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/" + ConfigConstant.API + "/*");
		registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registration;
	}

	@Bean
	public FilterRegistrationBean loggingfilterRegistrationBean() {

		final FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(loggingFilter());
		registration.addUrlPatterns("/*");
		registration.setName("loggingFilter");
		registration.setOrder(1);
		registration.setAsyncSupported(true);
		return registration;
	}

	@Bean(name = "loggingFilter")
	public Filter loggingFilter() {
		return new LoggingFilter();
	}

	// SOLO SI HAY INSTRUMENTATION SE PUEDE ACTIVAR MEDIANTE PROPIEDAD SI SE QUIERE.
//	@Bean
//	public FilterRegistrationBean instrumentationfilterRegistrationBean() {
//
//		final FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(instrumentationFilter());
//		registration.addUrlPatterns("/*");
//		registration.setName("instrumentation");
//		registration.setOrder(2);
//		registration.setAsyncSupported(true);
//		return registration;
//	}
//
//	@Bean(name = "instrumentationFilter")
//	public Filter instrumentationFilter() {
//		return new InstrumentationFilter();
//	}

}
