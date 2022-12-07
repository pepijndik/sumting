package app.server.Amazon;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.validation.constraints.NotBlank;

@Configuration

@ConfigurationProperties(prefix = "aws.s3")
@Getter
@Setter
public class AmazonConfig {
    @NotBlank
    private String accesskey;
    @NotBlank
    private String secretkey;

    @NotBlank
    private String region;

    /**
     * The name of the bucket
     */
    @NotBlank
    private String bucketproof;
    @NotBlank
    private String bucketbase;

    @Bean
    public AmazonS3 s3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.accesskey, this.secretkey);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(this.region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }

}
