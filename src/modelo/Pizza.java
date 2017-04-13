package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Pizza {

    Precios precios = new Precios();
    private double precioTotal;
    private String masa;
    private String tipoPizza;
    private String tamano;
    private boolean seleccionarCarpeta = false;
    private File dir = null;

    public final Set<String> preciosExtra = new HashSet<>();
    public final Set<String> nuestrasPizzas = new HashSet<>();

    public Pizza(String masa, String tipoPizza, String tamano) {
        this.masa = masa;
        this.tipoPizza = tipoPizza;
        this.tamano = tamano;
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

    public double calcularPrecio() {

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
        if (precios.buscarPrecio(tamano) != 0.0) {
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd - HH'h' mm'm' ss's'");
        formato = date.format(formatter);

//        if (seleccionarCarpeta == false) {
//            DirectoryChooser df = new DirectoryChooser();
//            dir = df.showDialog(new Stage());
//            seleccionarCarpeta = true;
//        } //Lo hize con el directoryChooser tambien, pero prefiro que guarde el tiquet en la carpeta directamente que yo he elegido
        if (tipoTicket.equalsIgnoreCase("pizzaGusto")) {

            Path fichero = Paths.get("PizzaGusto - " + formato + "-ticket.txt");
            String destino = ".\\src\\pizzav10\\Tickets\\" + fichero;
            Path directorio = Paths.get(destino);

            for (String ingredientes : preciosExtra) {
                textoIngredinetes += ingredientes.toUpperCase() + ": " + precios.buscarPrecio(ingredientes) + "€\n";
            }

            texto += "MASA --> " + this.masa + ": " + precios.buscarPrecio(this.masa) + "€"
                    + "\nTIPO DE PIZZA --> " + this.tipoPizza + ": " + precios.buscarPrecio(this.tipoPizza) + "€"
                    + "\nTAMAÑO PIZZA --> " + this.tamano + ": " + precios.buscarPrecio(this.tamano) + "%"
                    + "\nINGREDITENES\n" + textoIngredinetes;

            try (BufferedWriter salida = Files.newBufferedWriter(directorio.toAbsolutePath(), StandardOpenOption.CREATE)) {

                salida.write(texto + "----------------------\nPRECIO TOTAL: " + this.calcularPrecio() + "€\n¡Gracias por su compra!");

            }
//           try (Writer writer = new BufferedWriter(new OutputStreamWriter(
//                new FileOutputStream(formato + "-ticket.txt"), "utf-8"))) {
//            writer.write(texto + "----------------------\nPRECIO TOTAL: " + this.calcularPrecio() + "€\n¡Gracias por su compra!");
//        }
        } else if (tipoTicket.equalsIgnoreCase("nuestrasPizzas")){
            double total = 0.0;
            
            Path fichero = Paths.get("NuestrasPizzas - " + formato + "-ticket.txt");
            String destino = ".\\src\\pizzav10\\Tickets\\" + fichero;
            Path directorio = Paths.get(destino);

            for (String pizzas : nuestrasPizzas) {
                total += precios.buscarPrecio(pizzas); 
                texto += pizzas + ": " + precios.buscarPrecio(pizzas) + "€\n";
            }


            try (BufferedWriter salida = Files.newBufferedWriter(directorio.toAbsolutePath(), StandardOpenOption.CREATE)) {
                salida.write(texto + "----------------------\nPRECIO TOTAL: " + total + "€\n¡Gracias por su compra!");
            }
        }

    }

    public void cargarPrecios(File archivoOrigen) throws IOException {
        String leido, categoria, cantidad;
        double precio;
        String destino = archivoOrigen.getAbsolutePath();
        Path archivo = Paths.get(destino);

        Stream<String> datos = Files.lines(archivo);
        Iterator<String> it = datos.iterator();
        while (it.hasNext()) {
            leido = it.next();

            StringTokenizer t1 = new StringTokenizer(leido, ":");

            categoria = t1.nextToken();
            cantidad = t1.nextToken();
            precio = Double.parseDouble(cantidad);

            precios.precios.put(categoria, precio);
        }

    }

}
