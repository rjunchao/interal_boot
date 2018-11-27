package cn.rdp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("cn.rdp.**.mapper")
@SpringBootApplication
@EnableCaching
public class RDPApplication /*extends SpringBootServletInitializer*/ {
	
    public static void main(String[] args) {
        SpringApplication.run(RDPApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ  线下积分系统4.0启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                " ______                    _   ______            \n" +
                "|_   _ \\                  / |_|_   _ `.          \n" +
                "  | |_) |   .--.    .--. `| |-' | | `. \\  .--.   \n" +
                "  |  __'. / .'`\\ \\/ .'`\\ \\| |   | |  | |/ .'`\\ \\ \n" +
                " _| |__) || \\__. || \\__. || |, _| |_.' /| \\__. | \n" +
                "|_______/  '.__.'  '.__.' \\__/|______.'  '.__.'  ");
    }
    
   /* protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RDPApplication.class);
    }*/
}
