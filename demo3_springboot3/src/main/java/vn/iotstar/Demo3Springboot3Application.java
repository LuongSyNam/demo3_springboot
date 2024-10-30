package vn.iotstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import vn.iotstar.configs.MySiteMeshFilter;

@SpringBootApplication
public class Demo3Springboot3Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo3Springboot3Application.class, args);
	}
	
	public FilterRegistrationBean<MySiteMeshFilter> siteMeshFilter(){
		FilterRegistrationBean<MySiteMeshFilter> registrationBean = new FilterRegistrationBean<MySiteMeshFilter>();
		registrationBean.setFilter(new MySiteMeshFilter());
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
}
