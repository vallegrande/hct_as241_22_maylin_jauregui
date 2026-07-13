package hctjaureguimaylin.hackathon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import hctjaureguimaylin.hackathon.model.Cliente;
import hctjaureguimaylin.hackathon.repository.ClienteRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }
    
    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
    }
    
    public Cliente createCliente(Cliente cliente) {
        if (cliente.getId() == null) {
            Integer newId = generateNextId();
            cliente.setId(newId);
        } else if (clienteRepository.existsById(cliente.getId())) {
            throw new IllegalArgumentException("Ya existe un cliente con el ID: " + cliente.getId());
        }
        return clienteRepository.save(cliente);
    }

    private Integer generateNextId() {
        long count = clienteRepository.count();
        return (int) count + 1;
    }

    public Cliente updateCliente(Integer id, Cliente clienteDetails) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        
        cliente.setDni(clienteDetails.getDni());
        cliente.setNombres(clienteDetails.getNombres());
        cliente.setApellidos(clienteDetails.getApellidos());
        cliente.setCelular(clienteDetails.getCelular());
        cliente.setCorreo(clienteDetails.getCorreo());
        cliente.setLicencia(clienteDetails.getLicencia());
        cliente.setEstado(clienteDetails.getEstado());
        
        return clienteRepository.save(cliente);
    }
    
    public void deleteCliente(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }
}
