package es.iti.wakamiti.plugins.jmeter.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

import es.iti.wakamiti.plugins.jmeter.JMeterStepContributor;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import us.abstracta.jmeter.javadsl.core.threadgroups.DslDefaultThreadGroup;

import us.abstracta.jmeter.javadsl.core.TestPlanStats;
import es.iti.wakamiti.api.WakamitiConfiguration;
import es.iti.wakamiti.core.junit.WakamitiJUnitRunner;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import imconfig.AnnotatedConfiguration;
import imconfig.Configuration;
import imconfig.Property;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;
import java.io.IOException;
import java.time.Duration;

@RunWith(WakamitiJUnitRunner.class)
@AnnotatedConfiguration({
        @Property(key = WakamitiConfiguration.RESOURCE_TYPES, value = "gherkin"),
        @Property(key = WakamitiConfiguration.RESOURCE_PATH, value = "src/test/resources/features"),
})

public class JMeterTest {

    private static final ClientAndServer client = startClientAndServer(8888);


    @BeforeClass
    public static void setupServer() {
        client.when(HttpRequest.request().withPath("/")).respond(HttpResponse.response().withStatusCode(200));
    }


    @AfterClass
    public static void teardownServer() {
        client.close();
    }

}