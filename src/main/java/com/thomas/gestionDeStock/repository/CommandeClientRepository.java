package com.thomas.gestionDeStock.repository;

import com.thomas.gestionDeStock.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeClientRepository extends JpaRepository<CommandeClient, Integer> {
}
