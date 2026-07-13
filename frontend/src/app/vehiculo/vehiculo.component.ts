import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VehiculoService } from '../vehiculo.service';
import { Vehiculo } from '../vehiculo.interface';

@Component({
  selector: 'app-vehiculo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehiculo.component.html',
  styleUrl: './vehiculo.component.css'
})
export class VehiculoComponent implements OnInit {
  vehiculos: Vehiculo[] = [];
  vehiculoForm: Vehiculo = {
    id: '',
    placa: '',
    marca: '',
    modelo: '',
    anio: '',
    color: '',
    precio: 0,
    activo: true
  };
  isEditing = false;
  alertMessage = '';
  alertType = '';

  constructor(private vehiculoService: VehiculoService) {}

  ngOnInit(): void {
    this.loadVehiculos();
  }

  loadVehiculos(): void {
    this.vehiculoService.getAllVehiculos().subscribe({
      next: (data) => {
        this.vehiculos = data;
        this.showAlert('Vehículos cargados exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al cargar vehículos:', error);
        this.showAlert('Error al cargar vehículos', 'error');
      }
    });
  }

  onSubmit(): void {
    if (this.isEditing) {
      this.updateVehiculo();
    } else {
      this.createVehiculo();
    }
  }

  createVehiculo(): void {
    if (!this.vehiculoForm.placa || !this.vehiculoForm.marca || 
        !this.vehiculoForm.modelo || !this.vehiculoForm.anio || !this.vehiculoForm.color) {
      this.showAlert('Por favor complete todos los campos requeridos', 'error');
      return;
    }

    // Enviar ID vacío para que el backend lo genere automáticamente
    const vehiculoToCreate = { ...this.vehiculoForm, id: '' };

    this.vehiculoService.createVehiculo(vehiculoToCreate).subscribe({
      next: (data) => {
        this.vehiculos.push(data);
        this.resetForm();
        this.showAlert('Vehículo creado exitosamente con ID: ' + data.id, 'success');
      },
      error: (error) => {
        console.error('Error al crear vehículo:', error);
        this.showAlert('Error al crear vehículo: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  updateVehiculo(): void {
    this.vehiculoService.updateVehiculo(this.vehiculoForm.id, this.vehiculoForm).subscribe({
      next: (data) => {
        const index = this.vehiculos.findIndex(v => v.id === data.id);
        if (index !== -1) {
          this.vehiculos[index] = data;
        }
        this.resetForm();
        this.showAlert('Vehículo actualizado exitosamente', 'success');
      },
      error: (error) => {
        console.error('Error al actualizar vehículo:', error);
        this.showAlert('Error al actualizar vehículo: ' + (error.error?.message || error.message), 'error');
      }
    });
  }

  editVehiculo(vehiculo: Vehiculo): void {
    this.vehiculoForm = { ...vehiculo };
    this.isEditing = true;
    this.showAlert('Modo edición activado', 'info');
  }

  deleteVehiculo(id: string): void {
    if (confirm('¿Está seguro de que desea eliminar este vehículo?')) {
      this.vehiculoService.deleteVehiculo(id).subscribe({
        next: () => {
          this.vehiculos = this.vehiculos.filter(v => v.id !== id);
          this.showAlert('Vehículo eliminado exitosamente', 'success');
        },
        error: (error) => {
          console.error('Error al eliminar vehículo:', error);
          this.showAlert('Error al eliminar vehículo: ' + (error.error?.message || error.message), 'error');
        }
      });
    }
  }

  resetForm(): void {
    this.vehiculoForm = {
      id: '',
      placa: '',
      marca: '',
      modelo: '',
      anio: '',
      color: '',
      precio: 0,
      activo: true
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
