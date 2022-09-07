package io.pivotal.producer.game;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface GameRepository extends ReactiveCrudRepository<GameEntity, UUID> {
}
