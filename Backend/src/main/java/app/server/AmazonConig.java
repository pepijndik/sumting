package app.server;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.NotBlank;

@Configuration
public class AmazonConig {
    @NotBlank
    private String awsAcceskey;
    @NotBlank
    private String awsSecretKey;

    @NotBlank
    private String awsRegion;

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.awsAcceskey, this.awsSecretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(this.awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }

}
