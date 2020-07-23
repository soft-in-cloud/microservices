package com.adriangraczyk.errorservice.repository;

import com.adriangraczyk.errorservice.domain.Error;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Error entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorRepository extends ReactiveMongoRepository<Error, String> {


    Flux<Error> findAllBy(Pageable pageable);

}
