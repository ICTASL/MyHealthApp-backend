package lk.gov.govtech.covid19;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@ComponentScan
@EnableCaching
@EnableScheduling
public class Covid19Application {

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Colombo"));   // It will set UTC timezone
        log.info("Spring boot application running in Asia/Colombo timezone :"+ new Date());

    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication springApplication = new SpringApplication(Covid19Application.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.run(args);
    }

}
