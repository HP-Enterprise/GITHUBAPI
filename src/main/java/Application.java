import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Administrator on 2016/2/19 0019.
 */

@SpringBootApplication
@ComponentScan("com.incar")
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
