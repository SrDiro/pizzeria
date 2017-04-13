package modelo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Stream;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Precios {

    public final Map<String, Double> precios = new HashMap<>();

    public void modificarPrecios(String nombre, double cantidad) {
        precios.computeIfPresent(nombre, (k, v) -> cantidad);
    }

    public double buscarPrecio(String nombre) {
        double precio;
        if (precios.get(nombre) == null) {
            precio = 0.0;
        } else {
            precio = precios.get(nombre);
        }

        return precio;
    }

    public void cargarPrecios(File archivoOrigen) {
        String leido, categoria, cantidad;
        double precio;
        String destino = archivoOrigen.getAbsolutePath();
        Path archivo = Paths.get(destino);

        try (Stream<String> datos = Files.lines(archivo)) {
            Iterator<String> it = datos.iterator();
            while (it.hasNext()) {
                leido = it.next();

                StringTokenizer t1 = new StringTokenizer(leido, ":");

                categoria = t1.nextToken();
                cantidad = t1.nextToken();
                precio = Double.parseDouble(cantidad);

                precios.put(categoria, precio);
            }

            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("RedHotPizza");
            dialogoAlerta.setHeaderText("Los precios se han cargado con exito");

            Optional<ButtonType> result = dialogoAlerta.showAndWait();

        } catch (Exception e) {
            Alert dialogoAlerta = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlerta.setTitle("RedHotPizza");
            dialogoAlerta.setHeaderText("Error al cargar los precios");

            Optional<ButtonType> result = dialogoAlerta.showAndWait();
        }

    }

}
