package org.springframework.cloud.openfeign;

import feign.Target;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.bind.annotation.GetMapping;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Description:<br>
 * Date: 13/10/22-5:49 pm
 * @since
 * @author Jasbir Singh
 */
@SpringBootTest
@TestPropertySource("classpath:feign-properties.properties")
@DirtiesContext
public class FeignClientWithRefreshableUrlRegressionTest {

	@Autowired
	private Application.RegressionClient regressionClient;

	@Autowired
	private Application.RegressionClientWithUrl regressionClientWithUrl;

	@Autowired
	private Application.RegressionClientWithConfigUrl regressionClientWithConfigUrl;

	@Test
	public void shouldInstantiateFeignClientWhenUrlFromFeignClientName() {
		UrlTestClient.UrlResponseForTests response = regressionClient.test();
		assertThat(response.getUrl()).isEqualTo("http://regressionClient/test");
	}

	@Test
	public void shouldInstantiateFeignClientWhenUrlFromFeignClientUrl() {
		UrlTestClient.UrlResponseForTests response = regressionClientWithUrl.test();
		assertThat(response.getUrl()).isEqualTo("http://localhost:8081/test");
	}

	@Test
	public void shouldInstantiateFeignClientWhenTargetIsHardCodedTarget() {
		UrlTestClient.UrlResponseForTests response = regressionClientWithUrl.test();
		assertThat(response.getTargetType()).isEqualTo(Target.HardCodedTarget.class);
	}

	@Configuration
	@EnableAutoConfiguration
	@EnableConfigurationProperties(FeignClientProperties.class)
	@EnableFeignClients(clients = {Application.RegressionClient.class, Application.RegressionClientWithUrl.class, Application.RegressionClientWithConfigUrl.class})
	protected static class Application {

		@Bean
		UrlTestClient client() {
			return new UrlTestClient();
		}

		@FeignClient(name = "regressionClient")
		protected interface RegressionClient {

			@GetMapping("/test")
			UrlTestClient.UrlResponseForTests test();

		}

		@FeignClient(name = "regressionClientWithUrl", url = "http://localhost:8081")
		protected interface RegressionClientWithUrl {

			@GetMapping("/test")
			UrlTestClient.UrlResponseForTests test();

		}

		@FeignClient(name = "regressionClientWithConfigUrl")
		protected interface RegressionClientWithConfigUrl {

			@GetMapping("/test")
			UrlTestClient.UrlResponseForTests test();

		}
	}
}
