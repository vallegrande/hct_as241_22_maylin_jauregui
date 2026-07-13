import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../cliente.service';
import { Cliente } from '../cliente.interface';

@Component({
  selector: 'app-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cliente.component.html',
  styleUrl: './cliente.component.css'
})
export class ClienteComponent implements OnInit {
  clientes: Cliente[] = [];
  clienteForm: Cliente = {
    id: 0,
    dni: '',
    nombres: '',
    apellidos: '',
    celular: '',
    correo: '',
    licencia: '',
    estado: 'Activo'
  };
  isEditing = false;
  alertMessage = '';
  alertType = '';

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.clienteService.getAllClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.showAlert('Clientes cargados exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al cargar clientes:', error);
        this.showAlert('Error al cargar clientes', 'error');
      }
    });
  }

  onSubmit(): void {
    if (this.isEditing) {
      this.updateCliente();
    } else {
      this.createCliente();
    }
  }

  createCliente(): void {
    if (!this.clienteForm.dni || !this.clienteForm.nombres || 
        !this.clienteForm.apellidos || !this.clienteForm.celular || 
        !this.clienteForm.correo || !this.clienteForm.licencia) {
      this.showAlert('Por favor complete todos los campos requeridos', 'error');
      return;
    }

    // Enviar ID 0 para que el backend lo genere automáticamente
    const clienteToCreate = { ...this.clienteForm, id: 0 };

    this.clienteService.createCliente(clienteToCreate).subscribe({
      next: (data) => {
        this.clientes.push(data);
        this.resetForm();
        this.showAlert('Cliente creado exitosamente con ID: ' + data.id, 'success');
      },
      error: (error) => {
        console.error('Error al crear cliente:', error);
        this.showAlert('Error al crear cliente: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  updateCliente(): void {
    this.clienteService.updateCliente(this.clienteForm.id, this.clienteForm).subscribe({
      next: (data) => {
        const index = this.clientes.findIndex(c => c.id === data.id);
        if (index !== -1) {
          this.clientes[index] = data;
        }
        this.resetForm();
        this.showAlert('Cliente actualizado exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al actualizar cliente:', error);
        this.showAlert('Error al actualizar cliente: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  editCliente(cliente: Cliente): void {
    this.clienteForm = { ...cliente };
    this.isEditing = true;
    this.showAlert('Modo edición activado', 'info');
  }

  deleteCliente(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar este cliente?')) {
      this.clienteService.deleteCliente(id).subscribe({
        next: () => {
          this.clientes = this.clientes.filter(c => c.id !== id);
          this.showAlert('Cliente eliminado exitosamente', 'success');
        },
        error: (error) => {
          console.error('Error al eliminar cliente:', error);
          this.showAlert('Error al eliminar cliente: ' + (error.error?.message || error.message), 'error');
        }
      });
    }
  }

  resetForm(): void {
    this.clienteForm = {
      id: 0,
      dni: '',
      nombres: '',
      apellidos: '',
      celular: '',
      correo: '',
      licencia: '',
      estado: 'Activo'
    };
    this.isEditing = false;
  }

  showAlert(message: string, type: string): void {
    this.alertMessage = message;
    this.alertType = type;
    setTimeout(() => {
      this.alertMessage = '';
      this.alertType = '';
    }, 3000);
  }
}
