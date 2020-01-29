package priv.markingxs.mpic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "priv.markingxs.mpic.user_login")
public class MpicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MpicApplication.class, args);
	}



}
