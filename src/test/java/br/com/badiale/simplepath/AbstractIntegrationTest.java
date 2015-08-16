package br.com.badiale.simplepath;

import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {
        SimplePathApplication.class,
        AbstractIntegrationTest.TestConfiguration.class
})
@Transactional
public class AbstractIntegrationTest {
    @Configuration
    public static class TestConfiguration {
        @Bean
        public GraphDatabaseService graphDatabaseService() {
            return new GraphDatabaseFactory().newEmbeddedDatabase(Neo4JConfiguration.GRAPH_DB + "-test");
        }
    }
}
