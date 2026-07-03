package hctjaureguimaylin.hackathon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hctjaureguimaylin.hackathon.model.Vehiculo;
import hctjaureguimaylin.hackathon.service.VehiculoService;

import java.util.List;
@RestController
@RequestMapping("/v1/api/vehiculo")
@RequiredArgsConstructor

public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAllVehiculos() {
        List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos();
        return ResponseEntity.ok(vehiculos);
    }
    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@RequestBody Vehiculo vehiculo) {
        Vehiculo createdVehiculo = vehiculoService.createVehiculo(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehiculo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable String id, @RequestBody Vehiculo vehiculoDetails) {
        try {
            Vehiculo updatedVehiculo = vehiculoService.updateVehiculo(id, vehiculoDetails);
            return ResponseEntity.ok(updatedVehiculo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculo(@PathVariable String id) {
        try {
            vehiculoService.deleteVehiculo(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
