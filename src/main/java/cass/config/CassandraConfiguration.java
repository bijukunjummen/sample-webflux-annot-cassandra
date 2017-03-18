package cass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractReactiveCassandraConfiguration;

@Configuration
public class CassandraConfiguration extends AbstractReactiveCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "sample";
    }
    
}
