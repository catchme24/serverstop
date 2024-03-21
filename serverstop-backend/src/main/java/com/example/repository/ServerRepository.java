package com.example.repository;


import com.example.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    Optional<Server> findById(Long id);
    List<Server> findAllByChronicleIgnoreCaseAndServerRate(String chronicle, String serverRate);

    List<Server> findAllByChronicleIgnoreCase(String chrnicle);

    List<Server> findAllByServerRate(String serverRate);

    @Query(value = "SELECT DISTINCT chronicle FROM server", nativeQuery = true)
    List<String> findAllChroicles();

    @Query(value = "SELECT DISTINCT server_rate FROM server", nativeQuery = true)
    List<String> findAllRates();

}
