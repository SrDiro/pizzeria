package modelo;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Pizza {

    Precios precios = new Precios();

    private double precioTotal;
    private String masa;
    private String tipoPizza;
    private String tamano;
    static int contador;
    public final Set<String> preciosExtra = new HashSet<>();
//    public final Set<String> preciosExtra = new HashSet<>();

    public Pizza(String masa, String tipoPizza, String tamano) {
        this.masa = masa;
        this.tipoPizza = tipoPizza;
        this.tamano = tamano;
        contador++;
    }

    public Pizza() {
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public void setTipoPizza(String tipoPizza) {
        this.tipoPizza = tipoPizza;
    }

    public String getMasa() {
        return masa;
    }

    public String getTipoPizza() {
        return tipoPizza;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public void setPreciosExtra(String ingrediente) {
        preciosExtra.add(ingrediente);
    }

    public void limpiarPreciosExtra() {
        preciosExtra.clear();
    }

    public String lista() {
        String respuesta = "";

        for (String ingredientes : preciosExtra) {
            respuesta += ingredientes + " ";
        }

        return respuesta;
    }

    public double calcularPrecio() { // Se queda aqui

        //ATRIBUTOS
        double total, precioMasa, precioTipo, precioIngredientes = 0.0, precioTamano = 1.0, totalFormateado, precio;

        //CALCULO PRECIO TIPO MASA
        precioMasa = precios.buscarPrecio(this.masa);

        //CALCULO PRECIO TIPO DE PIZZA
        precioTipo = precios.buscarPrecio(this.tipoPizza);

        //CALCULO PRECIO INGREDIENTES
        for (String ingrediente : this.preciosExtra) {
            precio = precios.buscarPrecio(ingrediente);
            precioIngredientes = precioIngredientes + precio;
        }

        //CALCULO PRECIO TAMAÑO
        if (precios.buscarPrecio(getTamano()) != 0.0) {
            precioTamano = precios.buscarPrecio(getTamano());
        }

        total = precioMasa + precioTipo + precioIngredientes;
        total = total * precioTamano;
        totalFormateado = Math.round(total * 100.0) / 100.0;

        return totalFormateado;
    }

    public void generarTicket(String tipoTicket) throws IOException {
        LocalDateTime date = LocalDateTime.now();
        String formato;
        String texto = "";
        String textoIngredinetes = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH'h' mm'm' ss's'");
        formato = date.format(formatter);

        Path fichero = Paths.get(formato + "-ticket.txt");

        if (tipoTicket.equalsIgnoreCase("pizzaGusto")) {

            for (String ingredientes : preciosExtra) {
                textoIngredinetes += ingredientes.toUpperCase() + ": " + precios.buscarPrecio(ingredientes) + "€\n";
            }

            texto += "MASA --> " + this.masa + ": " + precios.buscarPrecio(this.masa) + "€"
                    + "\nTIPO DE PIZZA --> " + this.tipoPizza + ": " + precios.buscarPrecio(this.tipoPizza) + "€"
                    + "\nTAMAÑO PIZZA --> " + this.tamano + ": " + precios.buscarPrecio(this.tamano) + "%"
                    + "\nINGREDITENES\n" + textoIngredinetes;
            try (Writer bf = Files.newBufferedWriter(fichero)) {
                bf.write(texto + "----------------------\nPRECIO TOTAL: " + this.calcularPrecio() + "€\n¡Gracias por su compra!");
            }
//           try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream(formato + "-ticket.txt"), "utf-8"))) {
//            writer.write(texto + "----------------------\nPRECIO TOTAL: " + this.calcularPrecio() + "€\n¡Gracias por su compra!");
//        }
        } else {
            
            texto +="";
            
            try (Writer bf = Files.newBufferedWriter(fichero)) {
                bf.write(texto + "€\n¡Gracias por su compra!");
            }            
        }
    }

}
