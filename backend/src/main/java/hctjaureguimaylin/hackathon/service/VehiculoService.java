package hctjaureguimaylin.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import hctjaureguimaylin.hackathon.model.Vehiculo;
import hctjaureguimaylin.hackathon.repository.VehiculoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }
    
    public Vehiculo createVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.existsById(vehiculo.getId())) {
            throw new IllegalArgumentException("Ya existe un vehiculo con el ID: " + vehiculo.getId());
        }
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo updateVehiculo(String id, Vehiculo vehiculoDetails) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado con ID: " + id));
        
        vehiculo.setPlaca(vehiculoDetails.getPlaca());
        vehiculo.setMarca(vehiculoDetails.getMarca());
        vehiculo.setModelo(vehiculoDetails.getModelo());
        vehiculo.setAnio(vehiculoDetails.getAnio());
        vehiculo.setColor(vehiculoDetails.getColor());
        vehiculo.setPrecio(vehiculoDetails.getPrecio());
        vehiculo.setActivo(vehiculoDetails.getActivo());
        
        return vehiculoRepository.save(vehiculo);
    }
    
    public void deleteVehiculo(String id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehiculo no encontrado con ID: " + id));
        vehiculoRepository.delete(vehiculo);
    }
}