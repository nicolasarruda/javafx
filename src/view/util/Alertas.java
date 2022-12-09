package view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alertas {
    
    public static Boolean confirmar;

    public static void showAlert(String title, String header, String content, AlertType type) {
	Alert alert = new Alert(type);
	alert.setTitle(title);
	alert.setHeaderText(header);
	alert.setContentText(content);
	alert.show();
    }
    
    public static void showAlertConfirmation(String title, String header, String content) {
	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	ButtonType btnConfirmar = new ButtonType("Confirmar");
	ButtonType btnCancelar = new ButtonType("Cancelar");
	
	alert.setTitle(title);
	alert.setHeaderText(header);
	alert.setContentText(content);
	alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);
	alert.showAndWait().ifPresent(button -> {
	    if(button == btnConfirmar) {
		confirmar = true;
	    } else {
		confirmar = false;
	    }
	});
	
    }
}
