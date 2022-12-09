package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import db.CategoriaDB;
import db.MovimentacaoDB;
import db.TiposMovimentacaoDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Categoria;
import model.entities.Movimentacao;
import model.entities.TiposMovimentacao;
import view.listeners.FormularioListener;
import view.util.Alertas;
import view.util.Formato;
import view.util.Restricao;

public class FormViewController implements Initializable, FormularioListener {

    TiposMovimentacaoDB tiposDB = new TiposMovimentacaoDB();
    
    CategoriaDB categoriaDB = new CategoriaDB();

    String pagamentoOpcoes[] = { "Sim", "Não" };
    
    List<FormularioListener> formularioListeners = new ArrayList<>();

    @FXML
    private Label formTitle;

    @FXML
    private Label formFlowLabel;

    @FXML
    private Label formPaymentLabel;

    @FXML
    private Label formCategoryLabel;

    @FXML
    private Label formValueLabel;

    @FXML
    private Label formDescriptionLabel;

    @FXML
    private ComboBox<TiposMovimentacao> formFlowComboBox;

    private ObservableList<TiposMovimentacao> obsTiposMovLista;

    @FXML
    private ComboBox<String> formPaymentComboBox;

    private ObservableList<String> obsPagamentoLista;

    @FXML
    private ComboBox<Categoria> formCategoryComboBox;

    private ObservableList<Categoria> obsCategoriaLista;

    @FXML
    private TextField formValueTextField;

    @FXML
    private TextField formDescriptionTextField;

    @FXML
    private Button btnAddFlowButton;

    @FXML
    private Button btnCancelFlowButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	recuperarTiposMovimentacao(); 
	pagamentoRealizado(); 
	recuperarCategorias(); 
	validarValor(); 
	limiteCaracteresDescricao();
    }

    private void limiteCaracteresDescricao() {
	Restricao.restricaoQuantidadeCaracteres(formDescriptionTextField, 50);
    }

    private void validarValor() {
	Restricao.restricaoCaracteresNumericos(formValueTextField);
    }

    private void recuperarTiposMovimentacao() {
	List<TiposMovimentacao> tipos = tiposDB.tipos();
	obsTiposMovLista = FXCollections.observableArrayList(tipos);
	formFlowComboBox.setItems(obsTiposMovLista);
    }

    private void pagamentoRealizado() {
	obsPagamentoLista = FXCollections.observableArrayList(pagamentoOpcoes);
	formPaymentComboBox.setItems(obsPagamentoLista);
    }

    private void recuperarCategorias() {
	List<Categoria> categorias = categoriaDB.categorias();
	obsCategoriaLista = FXCollections.observableArrayList(categorias);
	formCategoryComboBox.setItems(obsCategoriaLista);
    }

    @FXML
    public void onCancelFlowAction() throws IOException {
	notificarSaidaFormulario();
	MainViewController mvController = new MainViewController();
	Main.setSceneMainView(mvController.atualizandoMainView());
    }

    @FXML
    public void onAddFlowAction() throws IOException {
	if(validarInsercao()) {
	    MovimentacaoDB movDB = new MovimentacaoDB(new Movimentacao());
	    movDB.inserirMovimentacao(receberDadosFormulario());
	    Alertas.showAlert("Movimentação inserida com sucesso", "SUCESSO!", "", AlertType.CONFIRMATION);
	    notificarSaidaFormulario();
	    MainViewController mvController = new MainViewController();
	    Main.setSceneMainView(mvController.atualizandoMainView());
	}
    }

    private boolean validarInsercao() {
	boolean movimentacao = formFlowComboBox.getSelectionModel().isEmpty();
	boolean pagamento = formPaymentComboBox.getSelectionModel().isEmpty();
	boolean categoria = formCategoryComboBox.getSelectionModel().isEmpty();
	boolean valor = formValueTextField.getText().isEmpty();
	
	String linhaMovimentacao = formFlowLabel.getText();
	String linhaPagamento = formPaymentLabel.getText();
	String linhaCategoria = formCategoryLabel.getText();
	String linhaValor = formValueLabel.getText();
	
	if (movimentacao || pagamento || categoria || valor ) {
	    Alertas.showAlert("Erro ao inserir movimentação", "Campos sem preenchimento",
		    "É obrigatório preencher: \n" 
		    + campoInvalido(linhaMovimentacao, movimentacao)  
		    + campoInvalido(linhaPagamento, pagamento)
		    + campoInvalido(linhaCategoria, categoria)
		    + campoInvalido(linhaValor, valor) 
		    , AlertType.ERROR);
	    return false;
	}
	
	return true;
    }

    private String campoInvalido(String linha, boolean campoInvalido) {
	return campoInvalido ? linha + "\n" : "";
    }
    
    public List<String> receberDadosFormulario() {
	Formato formato = new Formato();
	List<String> dadosDoFormulario = new ArrayList<>();
	
	String tipoMovimentacao = String.valueOf(formFlowComboBox.getSelectionModel().getSelectedItem().getId());
	dadosDoFormulario.add(tipoMovimentacao);
	
	String pagamento = formPaymentComboBox.getSelectionModel().getSelectedItem();
	String pagamentoFormatado = formato.tratandoDadosPagamento(pagamento);
	dadosDoFormulario.add(pagamentoFormatado);
	
	String categoria = String.valueOf(formCategoryComboBox.getSelectionModel().getSelectedItem().getId());
	dadosDoFormulario.add(categoria);
	
	String valor = formValueTextField.getText();
	String valorFormatado = formato.tratandoValor(valor);
	dadosDoFormulario.add(valorFormatado);
	
	String descricao = formDescriptionTextField.getText();
	dadosDoFormulario.add(descricao);
	
	return dadosDoFormulario;
    }
    
    public void inserirDadoTransferidoListener(FormularioListener listener) {
	formularioListeners.add(listener);
    }
    
    
    private void notificarSaidaFormulario() {
	
	for(FormularioListener listener : formularioListeners) {
	    listener.onBotaoFormularioAction();
	}
    }

    @Override
    public void onBotaoFormularioAction() {
	try {
	    onAddFlowAction();
	    onCancelFlowAction();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
