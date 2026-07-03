package hctjaureguimaylin.hackathon.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hctjaureguimaylin.hackathon.model.Vehiculo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


public interface  VehiculoRepository extends JpaRepository<Vehiculo, String> {

}

