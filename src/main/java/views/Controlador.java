package views;

import data.Persistencia;
import domain.Marca;
import domain.Sucursal;
import domain.Vehiculo;
import domain.VehiculoCombustible;
import domain.VehiculoElectrico;
import domain.VehiculoTipo;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class Controlador {
    
    public static ArrayList<VehiculoViewModel> getVehiculos(){
        ArrayList<VehiculoViewModel> vehiculos = new ArrayList<>();
        for(Vehiculo vehiculo : Persistencia.getVehiculos()) {
            vehiculos.add(new VehiculoViewModel(vehiculo));
        }
        return vehiculos;
    }
    public static ArrayList<Marca> getMarcas() {
    return Persistencia.getMarcas();
}
    
      public static ArrayList<Sucursal> getSucursales() {
    return Persistencia.getSucursales();
}
       public static void botonAgregar(AgregarVehiculoView vistaAgregar) {

    ArrayList<Marca> marcas = Persistencia.getMarcas();
    ArrayList<Sucursal> sucursales = Persistencia.getSucursales();

    VehiculoTipo tipo = (VehiculoTipo) vistaAgregar.getjComboBox1().getSelectedItem();

    String patente = vistaAgregar.getPatente().getText();
    Marca marca = (Marca) vistaAgregar.getjComboBox3().getSelectedItem();
    String modelo = vistaAgregar.getModelo().getText();
    int anio = Integer.parseInt(vistaAgregar.getAnio().getText());
    double capCar = Double.parseDouble(vistaAgregar.getCapacidad().getText());
    Sucursal sucursal = (Sucursal) vistaAgregar.getjComboBox2().getSelectedItem();
  
    Vehiculo v;

    if (tipo == VehiculoTipo.ELECTRICO) {

        double kwhBase = Double.parseDouble(vistaAgregar.getKwh().getText());

        v = new VehiculoElectrico(patente, marca, modelo, anio, capCar, sucursal, kwhBase);
    } else { // COMBUSTIBLE

         double litrosExtra = Double.parseDouble(vistaAgregar.getLitrosExtra().getText());
         double kmPorLitro = Double.parseDouble(vistaAgregar.getKmPorLitro().getText());

        v = new VehiculoCombustible(patente, marca, modelo, anio, capCar, sucursal, kmPorLitro,litrosExtra);
    }

    Persistencia.agregarVehiculos(v);

    vistaAgregar.limpiarCampos();
}
      
      
    public static double[] calcularConsumos(Map<String, Double> vehiculos){
        double consumoElectricos = 0;
        double consumoCombustible= 0;
        for(Map.Entry<String, Double> entry : vehiculos.entrySet()){
           double consumo = 0;
           Optional<Vehiculo> vehiculo = Persistencia.getVehiculo(entry.getKey());
           if(vehiculo.isPresent()){
               consumo = vehiculo.get().calcularConsumo(entry.getValue());
               consumoElectricos += vehiculo.get().esDe(VehiculoTipo.ELECTRICO) ? consumo : 0;
               consumoCombustible += vehiculo.get().esDe(VehiculoTipo.COMBUSTIBLE) ? consumo : 0;
           }
        }
        return new double[] {consumoElectricos, consumoCombustible};
    }
}
