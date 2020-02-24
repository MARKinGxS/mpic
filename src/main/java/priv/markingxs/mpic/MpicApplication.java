package priv.markingxs.mpic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@SpringBootApplication
@MapperScan(basePackages = {"priv.markingxs.mpic"})
public class MpicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpicApplication.class, args);
	}



}
