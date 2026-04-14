package domain;

import java.time.LocalDate;

public class VehiculoCombustible extends Vehiculo {
    private double kilometrosPorLitro;
    private double litrosExtra;

    public VehiculoCombustible(String patente, Marca marca, String modelo, int anio, double capacidadCarga,
                               Sucursal sucursal, double kilometrosPorLitro, double litrosExtra) {
        super(VehiculoTipo.COMBUSTIBLE, patente, marca, modelo, anio, capacidadCarga, sucursal);
        this.kilometrosPorLitro = kilometrosPorLitro;
        this.litrosExtra = litrosExtra;
    }

   
    
     public double getKilometrosPorLitro() {
        return kilometrosPorLitro;
    }

    public double getLitrosExtra() {
        return litrosExtra;
    }



    @Override
    
    public double calcularConsumo(double kilometros) {
        
    
        int anioActual = LocalDate.now().getYear();
    
        double total=kilometros/kilometrosPorLitro ;
        double extra;
        int antiguedad = anioActual - anio; 
        if (antiguedad > 5){
         
         extra= kilometros/15;   
         total=  total+ extra*litrosExtra;   
            
         
        }

        return total;
    }
}
