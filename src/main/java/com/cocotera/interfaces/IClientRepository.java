package com.cocotera.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cocotera.models.Client;

@Repository
public interface IClientRepository extends JpaRepository<Client, String> {
}
