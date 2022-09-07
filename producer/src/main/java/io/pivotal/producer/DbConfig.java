package io.pivotal.producer;

import io.pivotal.producer.game.GameEntity;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
public class DbConfig {

    @Value("classpath:schema.sql")
    Resource dbSchemaResource;

    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(dbSchemaResource));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    BeforeConvertCallback<GameEntity> idGeneratingCallback(DatabaseClient databaseClient) {
        return (gameEntity, sqlIdentifier) -> {
            if (gameEntity.getId() == null) {
                return Mono.just(gameEntity.toBuilder().id(UUID.randomUUID()).build());
            }
            return Mono.just(gameEntity);
        };
    }
}
