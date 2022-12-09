package view;

import java.io.IOException;

import application.Main;
import db.UsuarioDB;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Usuario;
import view.util.Alertas;

public class LoginViewController {

    @FXML
    private Label login;
    
    @FXML
    private Label password;
    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private TextField txtUserPassword;
    
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnPasswordRecovery;


    @FXML
    public void onBtnLoginAction() throws IOException {
	if (Autenticado()) {
	    MainViewController mvController = new MainViewController();
	    Main.setSceneMainView(mvController.atualizandoMainView());
	}
    }

    @FXML
    public void onBtnPasswordRecoveryAction() {
	try {
	    UsuarioDB usuarioDB = new UsuarioDB(new Usuario());
	    String nome = onTxtNomeUsuario();
	    String senha = usuarioDB.recuperarSenha(nome);
	    if (senha == null) {
		Alertas.showAlert("Erro ao recuperar senha", onTxtNomeUsuario() + " nao encontrado",
			"usuario nao existe", AlertType.ERROR);
	    } else {
		Alertas.showAlert("Senha cadastrada", onTxtNomeUsuario() + " encontrado. ", " Senha: " + senha,
			AlertType.INFORMATION);
	    }
	} catch (Exception e) {
	    Alertas.showAlert("Sem conexão", "Erro ao conectar com o banco", e.getMessage(), AlertType.ERROR);
	}
    }

    private boolean Autenticado() {
	try {
	    UsuarioDB usuarioDB = new UsuarioDB(new Usuario());
	    String nome = onTxtNomeUsuario();
	    String senha = onTxtSenhaUsuario();
	    boolean isAutenticado = usuarioDB.procurarUsuarioPorNomeSenha(nome, senha);

	    if (isAutenticado) {
		return true;
	    }
	    Alertas.showAlert("Erro ao logar", onTxtNomeUsuario() + " nao encontrado", "usuario or senha sao invalidos",
		    AlertType.ERROR);
	    return false;
	} catch (Exception e) {
	    Alertas.showAlert("Sem conexão", "Erro ao conectar com o banco", e.getMessage(), AlertType.ERROR);
	    return false;
	}
    }

    private String onTxtNomeUsuario() {
	return txtUsername.getText();
    }

    private String onTxtSenhaUsuario() {
	return txtUserPassword.getText();
    }
}
