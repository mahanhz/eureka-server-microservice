package com.amhzing.eurekaserver.integrationtest;

import com.amhzing.eurekaserver.EurekaServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurekaServerApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class EurekaServerApplicationTest {
    @Value("${local.server.port}")
    private int port = 0;

    @Value("${local.management.port}")
    private int managementPort = 0;

    @Value("${server.context-path}")
    private String serverContextPath;

    @Value("${management.context-path}")
    private String managementContextPath;

    @Test
    public void configurationAvailable() {
        final ResponseEntity<String> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + port + serverContextPath + "/config-message", String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void managementAvailable() {
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity(
                "http://localhost:" + managementPort + managementContextPath, Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void envPostAvailable() {
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        final ResponseEntity<Map> entity = new TestRestTemplate().postForEntity(
                "http://localhost:" + managementPort + managementContextPath + "/env", form, Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }
}
