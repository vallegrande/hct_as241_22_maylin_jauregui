export interface Alquiler {
  id: number;
  clienteId: number;
  vehiculoId: string;
  dias: number;
  fechaInicio: string;
  fechaFin: string;
  total: number;
  estado: string;
}
