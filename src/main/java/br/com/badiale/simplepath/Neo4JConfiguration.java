package br.com.badiale.simplepath;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configurações relacionadas ao Neo4J.
 */
@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class Neo4JConfiguration extends Neo4jConfiguration {
    public static final String GRAPH_DB = "target/graph.db";

    Neo4JConfiguration() {
        setBasePackage("br.com.badiale.simplepath");
    }

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        return new GraphDatabaseFactory().newEmbeddedDatabase(GRAPH_DB);
    }
}
