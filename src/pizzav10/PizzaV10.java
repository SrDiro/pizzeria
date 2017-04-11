package pizzav10;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PizzaV10 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        stage.getIcons().add(new Image("/pizzav10/img/icono.png")); //ICONO DE LA APLICACION
        stage.setTitle("RedHotPizza"); //TITULO DE LA APLICACION
        stage.setResizable(false); //EVITAR QUE SE PUEDA MODIFICAR EL TAMAÑO DE LA VENTANA
        stage.sizeToScene(); // AJUSTAR LA VENTANA AL TAMAÑO DEL PANE (PARA QUE NO HAYAN BORDES BLANCOS EN LA VENTANA)
        
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

//
//        stage = (Stage) dialog.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(
//                new Image(this.getClass().getResource("<image>.png").toString()));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
