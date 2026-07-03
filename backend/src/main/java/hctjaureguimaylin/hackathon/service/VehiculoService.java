package hctjaureguimaylin.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import hctjaureguimaylin.hackathon.model.Vehiculo;
import hctjaureguimaylin.hackathon.repository.VehiculoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica de negocio para la gestión de estudiantes.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad Student.
 */
@Service
@RequiredArgsConstructor
public class VehiculoService {


  private final VehiculoRepository vehiculoRepository;
    
    /**
     * Obtiene todos los estudiantes registrados en el sistema.
     * 
     * @return Lista de todos los estudiantes
     */
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findAll();
    }
    
    
    /**
     * Crea un nuevo estudiante en el sistema.
     * Valida que no exista otro estudiante con el mismo DNI.
     * 
     * @param vehiculo Estudiante a crear
     * @return El estudiante creado
     * @throws IllegalArgumentException si ya existe un estudiante con el mismo DNI
     */
    public Vehiculo createVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.existsById(vehiculo.getId())) {
            throw new IllegalArgumentException("Ya existe un estudiante con el ID: " + vehiculo.getId());
        }
        return vehiculoRepository.save(vehiculo);
    }

    /**
     * Actualiza la información de un estudiante existente.
     * 
     * @param id DNI del estudiante a actualizar
     * @param vehiculoDetails Nuevos datos del estudiante
     * @return El estudiante actualizado
     * @throws IllegalArgumentException si el estudiante no existe
     */
    public Vehiculo updateVehiculo(String id, Vehiculo vehiculoDetails) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + id));
        
        vehiculo.setPlaca(vehiculoDetails.getPlaca());
        vehiculo.setMarca(vehiculoDetails.getMarca());
        vehiculo.setModelo(vehiculoDetails.getModelo());
        // La fecha no se actualiza, se mantiene la original
        
        return vehiculoRepository.save(vehiculo);
    }
    
    /**
     * Elimina un estudiante del sistema por su DNI.
     * 
     * @param id DNI del estudiante a eliminar
     * @throws IllegalArgumentException si el estudiante no existe
     */
    public void deleteVehiculo(String id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con DNI: " + id));
        vehiculoRepository.delete(vehiculo);
    }
}