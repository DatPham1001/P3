package com.example.demo.repo;

import com.example.demo.domain.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepo extends JpaRepository<Configuration,Integer> {
}
