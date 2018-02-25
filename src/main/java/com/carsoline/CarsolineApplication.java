package com.carsoline;

import com.carsoline.rest.CarResource;
import com.carsoline.rest.EngineResource;
import com.carsoline.rest.EntryResource;
import com.carsoline.rest.UserResource;
import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CarsolineApplication extends Application {

	//public CarsolineApplication() {
	//	BeanConfig beanConfig = new BeanConfig();
	//	beanConfig.setVersion("1.0.2");
	//	beanConfig.setSchemes(new String[]{"http"});
	//	beanConfig.setHost("localhost:8080");
	//	beanConfig.setBasePath("");
	//	beanConfig.setResourcePackage("io.swagger.resources");
	//	beanConfig.setScan(true);
	//}


	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet();

		resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
		resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		resources.add(UserResource.class);
		resources.add(EngineResource.class);
		resources.add(CarResource.class);
		resources.add(EntryResource.class);

		return resources;
	}

	public static void main(String[] args) {
		SpringApplication.run(CarsolineApplication.class, args);
	}
}
