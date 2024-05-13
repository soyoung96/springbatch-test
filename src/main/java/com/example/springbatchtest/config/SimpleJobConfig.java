package com.example.springbatchtest.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job simpleJob(){
        return new JobBuilder("simpleJob1",jobRepository)
                .start(sampleStep1())
                .next(sampleStep2())
                .build();
    }
    @Bean
    public Step sampleStep1(){
        return new StepBuilder("simpleStep1",jobRepository)
                .tasklet(
                        (contribution, chunkContext) -> {
                    log.info(">> simpleStep1 executed");
                    return RepeatStatus.FINISHED;
                },transactionManager).build();
    }
    @Bean
    public Step sampleStep2(){
        return new StepBuilder("simpleStep2",jobRepository)
                .tasklet(
                        (contribution, chunkContext) -> {
                            log.info(">> simpleStep2 executed");
                            return RepeatStatus.FINISHED;
                        },transactionManager).build();

    }
}
