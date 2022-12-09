package view.util;

import javafx.scene.control.TextField;

public class Restricao {

    public static void restricaoCaracteresNumericos(TextField txt) {
	txt.textProperty().addListener((obs, valor, novoValor) -> {
	    if(novoValor != null && !novoValor.matches("\\d*([\\,]\\d*)?")) {
		txt.setText(valor);
	    }
	});
    }
    
    public static void restricaoQuantidadeCaracteres(TextField txt, int max) {
	txt.textProperty().addListener((obs, valor, novoValor) -> {
	    if(novoValor != null && novoValor.length() > max) {
		txt.setText(valor);
	    }
	});
    }
}
