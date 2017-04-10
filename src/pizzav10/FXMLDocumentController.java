package pizzav10;

import java.io.IOException;
import modelo.Pizza;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modelo.Precios;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label labelMasa;
    @FXML
    private Label labelTipoPizza;
    @FXML
    private Label labelIngredientes;
    @FXML
    private Label labelTamano;
    @FXML
    private RadioButton rbNormal;
    @FXML
    private RadioButton rbIntegral;
    @FXML
    private ComboBox<String> cbTipoPizza;
    @FXML
    private ListView<String> lvIngredientes;
    @FXML
    private Pane paneFuego;
    @FXML
    private Label labelTotal;
    @FXML
    private CheckBox cbCuatroQuesos;
    @FXML
    private CheckBox cbBarbacoa;
    @FXML
    private CheckBox cbMargarita;
    @FXML
    private Spinner<String> spinnerTamano;
    @FXML
    private Label precioFinalMasa;
    @FXML
    private Label precioFinalTipo;
    @FXML
    private Label precioFinalIngredientes;
    @FXML
    private Label precioFinalTamano;
    @FXML
    private Label precioFinalPedido;
    @FXML
    private Button botonMostrarTodos;
    @FXML
    private Label mostrarPrecioJamon;
    @FXML
    private Label mostrarPrecioQueso;
    @FXML
    private Label mostrarPrecioTomate;
    @FXML
    private Label mostrarPrecioCebolla;
    @FXML
    private Label mostrarPrecioOlivas;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Pane paneJamon;
    @FXML
    private Pane paneQueso;
    @FXML
    private Pane paneTomate;
    @FXML
    private Pane paneCebolla;
    @FXML
    private Pane paneOlivas;
    @FXML
    private Pane paneNotFound;
    @FXML
    private Label labelNotFound;
    @FXML
    private Pane paneLogin;
    @FXML
    private PasswordField fieldContrasenya;
    @FXML
    private TextField fieldUsuario;
    @FXML
    private Button botonLogin;
    @FXML
    private Pane paneCambiarPrecio;
    @FXML
    private Label mostrarPrecioJamon1;
    @FXML
    private Label mostrarPrecioQueso1;
    @FXML
    private Label mostrarPrecioTomate1;
    @FXML
    private Label mostrarPrecioCebolla1;
    @FXML
    private Label mostrarPrecioOlivas1;
    @FXML
    private TextField precioCambiarQueso;
    @FXML
    private TextField precioCambiarTomate;
    @FXML
    private TextField precioCambiarCebolla;
    @FXML
    private TextField precioCambiarOlivas;
    @FXML
    private Button botonAplicarCambios;
    @FXML
    private TextField precioCambiarJamon;
    @FXML
    private Button botonCerrarSesion;

    //LISTAS
    ObservableList<String> listaTipoPizzas = FXCollections.observableArrayList("Basica", "Cuatro Quesos", "Barbacoa", "Mexicana");
    ObservableList<String> listaIngredientes = FXCollections.observableArrayList("Sin extra", "Jamon", "Queso", "Tomate", "Cebolla", "Olivas", "Picante");
    ObservableList<String> listaTamanos = FXCollections.observableArrayList("Grande", "Mediana", "Pequeña");
    ObservableList<String> list = FXCollections.observableArrayList();

    //OBJETOS
    Pizza pizza = new Pizza();
    Precios precios = new Precios();

    //ATRIBUTOS
    String tipoMasa, tipoPizza, tamano;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipoPizza.setItems(listaTipoPizzas);
        cbTipoPizza.setValue("Tipo...");
        lvIngredientes.setItems(listaIngredientes);

        lvIngredientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        paneFuego.setVisible(false);

        //LABELS EN PEDIDO
        labelMasa.setVisible(false);
        labelTipoPizza.setVisible(false);
        labelIngredientes.setVisible(false);
        labelTamano.setVisible(false);

        //SPINNER (Hecho por mi)
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(listaTamanos);
        spinnerTamano.setValueFactory(valueFactory);
        valueFactory.setValue("Pequeña");
//        spinnerTamano.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);

//        Posiciones de las flechas de seleccion en el Spinner
//        STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL
//        STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL
//        STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL
//        STYLE_CLASS_SPLIT_ARROWS_VERTICAL
//        STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL
        //TAB PRECIOS
        paneNotFound.setStyle("-fx-background-image: url(img/404.gif); -fx-background-size: 207px 200px;");

        paneJamon.setVisible(false);
        paneQueso.setVisible(false);
        paneTomate.setVisible(false);
        paneCebolla.setVisible(false);
        paneOlivas.setVisible(false);
        paneNotFound.setVisible(false);
        labelNotFound.setVisible(false);

        //Administracion
        paneCambiarPrecio.setVisible(false);

    }

    // Nuestros productos
    @FXML
    private void comprarPizza(ActionEvent event) {
        double total, precioCuatroQuesos = 0.0, precioBarbacoa = 0.0, precioMargarita = 0.0;
        if (cbCuatroQuesos.isSelected()) {
            precioCuatroQuesos = 11.50;
        }
        if (cbBarbacoa.isSelected()) {
            precioBarbacoa = 10.50;
        }
        if (cbMargarita.isSelected()) {
            precioMargarita = 9.95;
        }

        total = precioCuatroQuesos + precioBarbacoa + precioMargarita;

        if (total == 0.0) {
            labelTotal.setText("0€");
        } else {
            labelTotal.setText(total + "€");
        }

    }

    //Pizza al gusto
    @FXML
    private void tipoMasa(ActionEvent event) {
        if (rbNormal.isSelected()) {
            pizza.setMasa("Normal");
            tipoMasa = "Normal";
            labelMasa.setText(tipoMasa);
            labelMasa.setVisible(true);

        } else if (rbIntegral.isSelected()) {
            pizza.setMasa("Integral");
            tipoMasa = "Integral";
            labelMasa.setText(tipoMasa);
            labelMasa.setVisible(true);

        }

        precioFinalMasa.setText(precios.buscarPrecio(tipoMasa) + "€");
        precioFinalPedido.setText(pizza.calcularPrecio() + "€");

    }

    @FXML
    private void tipoPizza(ActionEvent event) {

        tipoPizza = cbTipoPizza.getValue();
        labelTipoPizza.setText(tipoPizza);
        labelTipoPizza.setVisible(true);

        pizza.setTipoPizza(tipoPizza);
        precioFinalTipo.setText(precios.buscarPrecio(pizza.getTipoPizza()) + "€");
        precioFinalPedido.setText(pizza.calcularPrecio() + "€");

    }

    @FXML
    private void tipoIngredientes(MouseEvent event) {
        double total, precioSin = 0.0, precioJamon = 0.0, precioQueso = 0.0, precioTomate = 0.0, precioCebolla = 0.0, precioOlivas = 0.0;
        boolean apretado = false;
        String listaIngredientes = "";

        paneFuego.setVisible(false);
        ObservableList<String> selectedItems = lvIngredientes.getSelectionModel().getSelectedItems();
        pizza.limpiarPreciosExtra();

        for (String ingrediente : selectedItems) {
            pizza.setPreciosExtra(ingrediente);
            listaIngredientes = pizza.lista();

            if (ingrediente.equals("Sin extra")) {
                precioSin = precios.buscarPrecio(ingrediente);
                apretado = true;
            }
            if (ingrediente.equals("Jamon")) {
                precioJamon = precios.buscarPrecio(ingrediente);
            }
            if (ingrediente.equals("Queso")) {
                precioQueso = precios.buscarPrecio(ingrediente);
            }
            if (ingrediente.equals("Tomate")) {
                precioTomate = precios.buscarPrecio(ingrediente);
            }
            if (ingrediente.equals("Cebolla")) {
                precioCebolla = precios.buscarPrecio(ingrediente);
            }
            if (ingrediente.equals("Olivas")) {
                precioOlivas = precios.buscarPrecio(ingrediente);
            }

            if (ingrediente.equalsIgnoreCase("Picante")) {
                paneFuego.setVisible(true);
            }

        }

        if (apretado) {
            total = 0.0;
            labelIngredientes.setText("Sin extras");
            precioFinalIngredientes.setText(total + "€");
        } else {
            total = precioSin + precioJamon + precioQueso + precioTomate + precioCebolla + precioOlivas;
            labelIngredientes.setText(listaIngredientes);
            precioFinalIngredientes.setText(total + "€");
        }

        labelIngredientes.setVisible(true);

        precioFinalPedido.setText(pizza.calcularPrecio() + "€");

    }

    @FXML
    private void tamano(MouseEvent event) {
        String precioTamano = "", token = "";

        tamano = spinnerTamano.getValue();
        labelTamano.setVisible(true);
        labelTamano.setText(tamano);

        pizza.setTamano(tamano);
        if (tamano.equalsIgnoreCase("Pequeña")) {
            precioTamano = precios.buscarPrecio(tamano) + "";

        }
        if (tamano.equalsIgnoreCase("Mediana")) {
            precioTamano = precios.buscarPrecio(tamano) + "";
        }
        if (tamano.equalsIgnoreCase("Grande")) {
            precioTamano = precios.buscarPrecio(tamano) + "0";
        }
        StringTokenizer st = new StringTokenizer(precioTamano, ".");

        while (st.hasMoreTokens()) {
            token = st.nextToken();
        }
        precioFinalTamano.setText(token + "%");

        precioFinalPedido.setText(pizza.calcularPrecio() + "€");
    }

    @FXML
    private void mostrarTodosPrecios(ActionEvent event) {
        paneNotFound.setVisible(false);
        labelNotFound.setVisible(false);

        if (botonMostrarTodos.isFocused()) {
            paneJamon.setVisible(true);
            paneQueso.setVisible(true);
            paneTomate.setVisible(true);
            paneCebolla.setVisible(true);
            paneOlivas.setVisible(true);

            mostrarPrecioJamon.setText(precios.buscarPrecio("Jamon") + "€");
            mostrarPrecioQueso.setText(precios.buscarPrecio("Queso") + "€");
            mostrarPrecioTomate.setText(precios.buscarPrecio("Tomate") + "€");
            mostrarPrecioCebolla.setText(precios.buscarPrecio("Cebolla") + "€");
            mostrarPrecioOlivas.setText(precios.buscarPrecio("Olivas") + "€");

        }
    }

    @FXML
    private void mostrarPrecioIngrediente(ActionEvent event) {
        paneJamon.setVisible(false);
        paneQueso.setVisible(false);
        paneTomate.setVisible(false);
        paneCebolla.setVisible(false);
        paneOlivas.setVisible(false);
        paneNotFound.setVisible(false);
        labelNotFound.setVisible(false);

        Alert alertaFaltaNombre = new Alert(AlertType.WARNING);
        alertaFaltaNombre.setTitle("RedHotPizza");
        alertaFaltaNombre.setHeaderText("Por favor, introduzca el nombre del ingrediente primero.");

        Alert alertaNoEncontrado = new Alert(AlertType.INFORMATION);
        alertaNoEncontrado.setTitle("RedHotPizza");
        alertaNoEncontrado.setHeaderText("Lo sentimos, ingrediente no encontrado.");

        String ingrediente;

        ingrediente = tfBuscar.getText();

        if (ingrediente.isEmpty()) {
            alertaFaltaNombre.showAndWait();
            paneNotFound.setVisible(true);
            labelNotFound.setVisible(true);
        } else if (ingrediente.equalsIgnoreCase("Jamon") || ingrediente.equalsIgnoreCase("Jamón")) {
            mostrarPrecioJamon.setText(precios.buscarPrecio("Jamon") + "€");
            paneJamon.setVisible(true);
            paneJamon.setStyle("-fx-background-color: rgba(193,8,16,1);");
        } else if (ingrediente.equalsIgnoreCase("Queso")) {
            mostrarPrecioQueso.setText(precios.buscarPrecio("Queso") + "€");
            paneQueso.setVisible(true);
            paneQueso.setStyle("-fx-background-color: rgba(193,8,16,1);");
        } else if (ingrediente.equalsIgnoreCase("Tomate")) {
            mostrarPrecioTomate.setText(precios.buscarPrecio("Tomate") + "€");
            paneTomate.setVisible(true);
            paneTomate.setStyle("-fx-background-color: rgba(193,8,16,1);");
        } else if (ingrediente.equalsIgnoreCase("Cebolla")) {
            mostrarPrecioCebolla.setText(precios.buscarPrecio("Cebolla") + "€");
            paneCebolla.setVisible(true);
            paneCebolla.setStyle("-fx-background-color: rgba(193,8,16,1);");
        } else if (ingrediente.equalsIgnoreCase("Olivas")) {
            mostrarPrecioOlivas.setText(precios.buscarPrecio("Olivas") + "€");
            paneOlivas.setVisible(true);
            paneOlivas.setStyle("-fx-background-color: rgba(193,8,16,1);");
        } else {
            alertaNoEncontrado.showAndWait();
            paneNotFound.setVisible(true);
            labelNotFound.setVisible(true);
        }

    }

    @FXML
    private void loginAdministracion(ActionEvent event) {
        Alert alertaFaltaNombre = new Alert(AlertType.WARNING);
        alertaFaltaNombre.setTitle("RedHotPizza");
        alertaFaltaNombre.setHeaderText("Por favor, introduzca el nombre/usuario primero.");

        Alert alertaNoEncontrado = new Alert(AlertType.INFORMATION);
        alertaNoEncontrado.setTitle("RedHotPizza");
        alertaNoEncontrado.setHeaderText("Lo sentimos, nombre/usuario incorrecto.");

        boolean usuario = false, contrasenya = false, mostrado = false;
        String user, pass;

        user = fieldUsuario.getText();
        pass = fieldContrasenya.getText();

        if (botonLogin.isFocused()) {

            mostrarPrecioJamon1.setText(precios.buscarPrecio("Jamon") + "€");
            mostrarPrecioQueso1.setText(precios.buscarPrecio("Queso") + "€");
            mostrarPrecioTomate1.setText(precios.buscarPrecio("Tomate") + "€");
            mostrarPrecioCebolla1.setText(precios.buscarPrecio("Cebolla") + "€");
            mostrarPrecioOlivas1.setText(precios.buscarPrecio("Olivas") + "€");

            if (user.equals("admin")) {
                usuario = true;
            } else if (user.isEmpty()) {
                mostrado = true;
                alertaFaltaNombre.showAndWait();
            } else if (!mostrado) {
                alertaNoEncontrado.showAndWait();
            }

            if (pass.equals("admin")) {
                contrasenya = true;
            } else if (pass.isEmpty() && !mostrado) {
                alertaFaltaNombre.showAndWait();
            } else if (!mostrado) {
                alertaNoEncontrado.showAndWait();
            }

            if (usuario && contrasenya) {
                paneLogin.setVisible(false);
                paneCambiarPrecio.setVisible(true);
            }

        }

    }

    @FXML
    private void aplicarCambios(ActionEvent event) {

        double precioJ = -1, precioQ = -1, precioT = -1, precioC = -1, precioO = -1;
        String precioJamon, precioQueso, precioTomate, precioCebolla, precioOlivas;

        precioJamon = precioCambiarJamon.getText();
        precioQueso = precioCambiarQueso.getText();
        precioTomate = precioCambiarTomate.getText();
        precioCebolla = precioCambiarCebolla.getText();
        precioOlivas = precioCambiarOlivas.getText();

        if (!precioJamon.isEmpty()) {
            precioJ = Double.parseDouble(precioCambiarJamon.getText());
        }

        if (!precioTomate.isEmpty()) {
            precioT = Double.parseDouble(precioCambiarTomate.getText());
        }

        if (!precioQueso.isEmpty()) {
            precioQ = Double.parseDouble(precioCambiarQueso.getText());
        }

        if (!precioCebolla.isEmpty()) {
            precioC = Double.parseDouble(precioCambiarCebolla.getText());
        }

        if (!precioOlivas.isEmpty()) {
            precioC = Double.parseDouble(precioCambiarOlivas.getText());
        }

        if (botonAplicarCambios.isFocused()) {

            if (precioJ >= 0.0) {
                precios.modificarPrecios("Jamon", precioJ);
            }
            if (precioQ >= 0.0) {
                precios.modificarPrecios("Queso", precioQ);
            }
            if (precioT >= 0.0) {
                precios.modificarPrecios("Tomate", precioT);
            }
            if (precioC >= 0.0) {
                precios.modificarPrecios("Cebolla", precioC);
            }
            if (precioO >= 0.0) {
                precios.modificarPrecios("Olivas", precioO);
            }

        }

        precioCambiarJamon.setText("");
        precioCambiarCebolla.setText("");
        precioCambiarTomate.setText("");
        precioCambiarCebolla.setText("");
        precioCambiarOlivas.setText("");

        mostrarPrecioJamon1.setText(precios.buscarPrecio("Jamon") + "€");
        mostrarPrecioQueso1.setText(precios.buscarPrecio("Queso") + "€");
        mostrarPrecioTomate1.setText(precios.buscarPrecio("Tomate") + "€");
        mostrarPrecioCebolla1.setText(precios.buscarPrecio("Cebolla") + "€");
        mostrarPrecioOlivas1.setText(precios.buscarPrecio("Olivas") + "€");

    }

    @FXML
    private void cerrarSesion(ActionEvent event) {

        if (botonCerrarSesion.isFocused()) {
            paneLogin.setVisible(true);
            paneCambiarPrecio.setVisible(false);
            fieldUsuario.setText("");
            fieldContrasenya.setText("");
        }
    }

    @FXML
    private void generarTicket(ActionEvent event) throws IOException {
        pizza.generarTicket();
        
    }

}
