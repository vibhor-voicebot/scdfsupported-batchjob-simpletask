package io.spring.scdf.batch;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.spring.scdf.batch.support.MyJobLauncherCommandLineRunner;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class TestTaskBatchApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobExplorer jobExplorer;

	@Value("${sample.jobParams:null}")
	private String additionalJobParams;

	@Value("${sample.makeParametersUnique:true}")
	private boolean makeParametersUnique;

	public static void main(String[] args) {
		SpringApplication.run(TestTaskBatchApplication.class, args);
	}

	@Bean
	public MyJobLauncherCommandLineRunner commandLineRunner() {
		return new MyJobLauncherCommandLineRunner(jobLauncher, jobExplorer, additionalJobParams, makeParametersUnique);
	}
	
	@Autowired
	private ApplicationContext context;
	
    @RequestMapping(path = "/deregisterBeans")
    public void deregisterBeans()    {
        ConfigurableApplicationContext configContext = (ConfigurableApplicationContext)context;
    	BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) context.getAutowireCapableBeanFactory();
    	//BeanDefinition oldBeanDefinition = beanRegistry.getBeanDefinition("commandLineRunner");//this is needed if u want to revert your bean changes back to how it was
    	//beanRegistry.registerBeanDefinition("employeeBean", oldBeanDefinition);
    	SingletonBeanRegistry registry = configContext.getBeanFactory();
    	((AutowireCapableBeanFactory) registry).destroyBean("commandLineRunner"); 
        //return "Context root-path for springboot rest controller!";
    	//return entity;
  
        
    }
}
