package com.amhzing.eurekaserver.smoketest;

import com.amhzing.eurekaserver.EurekaServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EurekaServerApplication.class)
public class SmokeTest {

    @Value("${server.base-uri}")
    private String baseUri;

    @Value("${management.port}")
    private int managementPort;

    @Value("${management.context-path}")
    private String managementContextPath;

    @Test
    public void healthStatus() {
        final ResponseEntity<Map> entity = new TestRestTemplate().getForEntity(getUrl(), Map.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        final Map body = entity.getBody();
        assertTrue(body.containsKey("status"));
        assertEquals(body.get("status"), "UP");
    }

    private String getUrl() {
        return baseUri + ":" + managementPort + "/" + managementContextPath + "/health";
    }
}