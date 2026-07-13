package hctjaureguimaylin.hackathon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import hctjaureguimaylin.hackathon.model.Alquiler;
import hctjaureguimaylin.hackathon.service.AlquilerService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/alquiler")
@RequiredArgsConstructor

public class AlquilerController {

    private final AlquilerService alquilerService;

    @GetMapping
    public ResponseEntity<List<Alquiler>> getAllAlquileres() {
        List<Alquiler> alquileres = alquilerService.getAllAlquileres();
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alquiler> getAlquilerById(@PathVariable Integer id) {
        try {
            Alquiler alquiler = alquilerService.getAlquilerById(id);
            return ResponseEntity.ok(alquiler);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Alquiler> createAlquiler(@RequestBody Alquiler alquiler) {
        Alquiler createdAlquiler = alquilerService.createAlquiler(alquiler);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlquiler);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alquiler> updateAlquiler(@PathVariable Integer id, @RequestBody Alquiler alquilerDetails) {
        try {
            Alquiler updatedAlquiler = alquilerService.updateAlquiler(id, alquilerDetails);
            return ResponseEntity.ok(updatedAlquiler);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlquiler(@PathVariable Integer id) {
        try {
            alquilerService.deleteAlquiler(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
