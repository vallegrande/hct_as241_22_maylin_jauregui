import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlquilerService } from '../alquiler.service';
import { Alquiler } from '../alquiler.interface';

@Component({
  selector: 'app-alquiler',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './alquiler.component.html',
  styleUrl: './alquiler.component.css'
})
export class AlquilerComponent implements OnInit {
  alquileres: Alquiler[] = [];
  alquilerForm: Alquiler = {
    id: 0,
    clienteId: 0,
    vehiculoId: '',
    dias: 0,
    fechaInicio: '',
    fechaFin: '',
    total: 0,
    estado: 'Activo'
  };
  isEditing = false;
  alertMessage = '';
  alertType = '';

  constructor(private alquilerService: AlquilerService) {}

  ngOnInit(): void {
    this.loadAlquileres();
  }

  loadAlquileres(): void {
    this.alquilerService.getAllAlquileres().subscribe({
      next: (data) => {
        this.alquileres = data;
        this.showAlert('Alquileres cargados exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al cargar alquileres:', error);
        this.showAlert('Error al cargar alquileres', 'error');
      }
    });
  }

  onSubmit(): void {
    if (this.isEditing) {
      this.updateAlquiler();
    } else {
      this.createAlquiler();
    }
  }

  createAlquiler(): void {
    if (!this.alquilerForm.clienteId || !this.alquilerForm.vehiculoId || 
        !this.alquilerForm.dias || !this.alquilerForm.fechaInicio || 
        !this.alquilerForm.fechaFin || !this.alquilerForm.total) {
      this.showAlert('Por favor complete todos los campos requeridos', 'error');
      return;
    }

    // Enviar ID 0 para que el backend lo genere automáticamente
    const alquilerToCreate = { ...this.alquilerForm, id: 0 };

    this.alquilerService.createAlquiler(alquilerToCreate).subscribe({
      next: (data) => {
        this.alquileres.push(data);
        this.resetForm();
        this.showAlert('Alquiler creado exitosamente con ID: ' + data.id, 'success');
      },
      error: (error) => {
        console.error('Error al crear alquiler:', error);
        this.showAlert('Error al crear alquiler: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  updateAlquiler(): void {
    this.alquilerService.updateAlquiler(this.alquilerForm.id, this.alquilerForm).subscribe({
      next: (data) => {
        const index = this.alquileres.findIndex(a => a.id === data.id);
        if (index !== -1) {
          this.alquileres[index] = data;
        }
        this.resetForm();
        this.showAlert('Alquiler actualizado exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al actualizar alquiler:', error);
        this.showAlert('Error al actualizar alquiler: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  editAlquiler(alquiler: Alquiler): void {
    this.alquilerForm = { ...alquiler };
    this.isEditing = true;
    this.showAlert('Modo edición activado', 'info');
  }

  deleteAlquiler(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar este alquiler?')) {
      this.alquilerService.deleteAlquiler(id).subscribe({
        next: () => {
          this.alquileres = this.alquileres.filter(a => a.id !== id);
          this.showAlert('Alquiler eliminado exitosamente', 'success');
        },
        error: (error) => {
          console.error('Error al eliminar alquiler:', error);
          this.showAlert('Error al eliminar alquiler: ' + (error.error?.message || error.message), 'error');
        }
      });
    }
  }

  resetForm(): void {
    this.alquilerForm = {
      id: 0,
      clienteId: 0,
      vehiculoId: '',
      dias: 0,
      fechaInicio: '',
      fechaFin: '',
      total: 0,
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
