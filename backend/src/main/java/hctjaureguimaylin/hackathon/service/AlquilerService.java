package hctjaureguimaylin.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import hctjaureguimaylin.hackathon.model.Alquiler;
import hctjaureguimaylin.hackathon.repository.AlquilerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    
    public List<Alquiler> getAllAlquileres() {
        return alquilerRepository.findAll();
    }
    
    public Alquiler getAlquilerById(Integer id) {
        return alquilerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + id));
    }
    
    public Alquiler createAlquiler(Alquiler alquiler) {
        if (alquiler.getId() == null) {
            Integer newId = generateNextId();
            alquiler.setId(newId);
        } else if (alquilerRepository.existsById(alquiler.getId())) {
            throw new IllegalArgumentException("Ya existe un alquiler con el ID: " + alquiler.getId());
        }
        return alquilerRepository.save(alquiler);
    }

    private Integer generateNextId() {
        long count = alquilerRepository.count();
        return (int) count + 1;
    }

    public Alquiler updateAlquiler(Integer id, Alquiler alquilerDetails) {
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + id));
        
        alquiler.setClienteId(alquilerDetails.getClienteId());
        alquiler.setVehiculoId(alquilerDetails.getVehiculoId());
        alquiler.setDias(alquilerDetails.getDias());
        alquiler.setFechaInicio(alquilerDetails.getFechaInicio());
        alquiler.setFechaFin(alquilerDetails.getFechaFin());
        alquiler.setTotal(alquilerDetails.getTotal());
        alquiler.setEstado(alquilerDetails.getEstado());
        
        return alquilerRepository.save(alquiler);
    }
    
    public void deleteAlquiler(Integer id) {
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alquiler no encontrado con ID: " + id));
        alquilerRepository.delete(alquiler);
    }
}
