package hctjaureguimaylin.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import hctjaureguimaylin.hackathon.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
