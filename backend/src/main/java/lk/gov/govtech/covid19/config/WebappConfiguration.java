package lk.gov.govtech.covid19.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Paths;

@Slf4j
@Configuration
public class WebappConfiguration {

    @Bean
    @ConditionalOnProperty(name = "webapp.war.filename")
    public ServletWebServerFactory servletContainer(@Value("${webapp.war.filename}") String filename,
                                                    @Value("${webapp.war.context}") String contextPath) {

        return new TomcatServletWebServerFactory() {
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                //Springboot's embedded tomcat server does not have a webapp folder by default.
                //Thus, create.
                new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdirs();

                String source = getWarPath(filename);
                try {
                    if (source != null) {
                        tomcat.addWebapp(contextPath, Paths.get(source).toAbsolutePath().toString());
                    }
                } catch (Exception ex) {
                    log.error("Unable to find admin-webapp.war file. Starting backend without webapp");
                }
                return super.getTomcatWebServer(tomcat);
            }
        };
    }

    private String getWarPath(String filename){
        String webappTargetLocation = ".." + File.separator
                + "portal-frontend" + File.separator
                + "target" + File.separator
                + filename;

        if (new File(filename).exists()) {
            //Useful when running the jar -> check within the folder where the jar is
            return filename;

        } else if (new File(webappTargetLocation).exists()) {
            //Useful when running the mvn spring-boot:run command
            return webappTargetLocation;

        } else return null;
    }
}
