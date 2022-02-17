

package io.spring.scdf.batch.job;

import java.util.Random;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
@EnableBatchProcessing
@EnableTask
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${sample.jobName:}")
	private String jobName;

	@Bean
	public Job job() {

		final String jobNameToUse;

		if (StringUtils.hasText(this.jobName)) {
			jobNameToUse = this.jobName;
		}
		else {
			Random rand = new Random();
			jobNameToUse = "job" + rand.nextInt();
		}

		System.out.println("Setting up new Batch Job named: " + jobNameToUse);

		return jobBuilderFactory.get(jobNameToUse)
				.start(stepBuilderFactory.get("step1").tasklet(new HelloSimpleBatchTasklet()).build())
				.build();
	}
}
