import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehiculoComponent } from './vehiculo/vehiculo.component';
import { ClienteComponent } from './cliente/cliente.component';
import { AlquilerComponent } from './alquiler/alquiler.component';

@Component({
  selector: 'app-root',
  imports: [CommonModule, VehiculoComponent, ClienteComponent, AlquilerComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
  currentView = signal('vehiculo');

  setView(view: string) {
    this.currentView.set(view);
  }
}
