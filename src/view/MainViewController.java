package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import db.MovimentacaoDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Movimentacao;
import view.listeners.DadoTransferidoListener;
import view.util.Alertas;
import view.util.Formato;
import view.util.FormatoMovimentacao;

public class MainViewController implements Initializable, DadoTransferidoListener {

    Formato formato = new Formato();

    MovimentacaoDB movDB = new MovimentacaoDB(new Movimentacao());

    private List<DadoTransferidoListener> dadosModificadosListeners = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label currentMonth;

    @FXML
    private Label currentBalance;

    @FXML
    private Label currentBalanceResult;

    @FXML
    private Label previousBalance;

    @FXML
    private Label previousBalanceResult;

    @FXML
    private Label lastFlow;

    @FXML
    private Label lastFlowValue;

    @FXML
    private TableView<FormatoMovimentacao> tableViewFlow;

    @FXML
    private TableColumn<FormatoMovimentacao, String> tableColumnType;

    @FXML
    private TableColumn<FormatoMovimentacao, String> tableColumnCategory;

    @FXML
    private TableColumn<FormatoMovimentacao, Date> tableColumnDate;

    @FXML
    private TableColumn<FormatoMovimentacao, Double> tableColumnValue;

    @FXML
    private TableColumn<FormatoMovimentacao, String> tableColumnDescription;

    private ObservableList<FormatoMovimentacao> obsLista;

    @FXML
    private PieChart pieChart;

    private ObservableList<PieChart.Data> obsGrafico;

    @FXML
    private Button btnAddFlow;

    @FXML
    private Button btnDeleteFlow;

    @FXML
    private Button btnBack;

    @FXML
    public void btnBackAction() {
	Main.setSceneLoginView();
    }

    @FXML
    public void btnAddFlowAction() throws IOException {
	Main.setSceneFormView(atualizandoLoginView());
    }

    @FXML
    public void btnDeleteFlowAction() throws IOException {
	int indiceLinha = tableViewFlow.getSelectionModel().getSelectedIndex();
	FormatoMovimentacao mov = null;
	if (indiceLinha >= 0) {
	    mov = (FormatoMovimentacao) tableViewFlow.getItems().get(indiceLinha);
	}
	
	if (mov == null) {
	    Alertas.showAlert("Erro ao deletar", "Deletar linha da tabela", "Você deve selecionar uma linha tabela",
		    AlertType.ERROR);
	    return;
	}
	boolean confirmacao = false;
	Alertas.showAlertConfirmation("Deleção", "Confirmar deleção de linha",
		"Você realmente quer deletar esta linha?");
	confirmacao = Alertas.confirmar == null ? false : Alertas.confirmar;

	if (confirmacao) {
	    movDB.deletarLinhaTabela(mov.getId());
	    notificarDadosModificados();
	    Main.setSceneMainView(atualizandoMainView());
	}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	inicializarLinhas();
	inicializarTabela();
	inicializarGraficoPizza();
    }

    private void inicializarLinhas() {

	currentMonth.setText(formato.retornaMesAno());

	double saldoAtual = movDB.calculandoSaldoAtual();
	currentBalanceResult.setMaxWidth(300.0);
	
	if(saldoAtual < 0) {
	    currentBalanceResult.getStyleClass().add("linha-vermelha");
	} else {
	    currentBalanceResult.getStyleClass().add("linha-normal");
	}

	
	double saldoPrevisto = movDB.calculandoSaldoPrevisto();
	if(saldoPrevisto < 0) {
	    previousBalanceResult.getStyleClass().add("linha-vermelha");
	} else {
	    previousBalanceResult.getStyleClass().add("linha-normal");
	}
	previousBalanceResult.setMaxWidth(300.0);
	

	double ultimaMovimentacao = movDB.ultimaMovimentacao();
	lastFlowValue.setMaxWidth(300.0);
	lastFlowValue.setMaxHeight(40.0);
	
	
	currentBalanceResult.setText("R$ " + formato.formatoMoeda(saldoAtual));
	previousBalanceResult.setText("R$ " + formato.formatoMoeda(saldoPrevisto));
	lastFlowValue.setText("R$ " + formato.formatoMoeda(ultimaMovimentacao));
    }

    private void inicializarTabela() {
	tableColumnType.setCellValueFactory(mov -> new SimpleStringProperty(mov.getValue().getTipo()));
	tableColumnCategory.setCellValueFactory(mov -> new SimpleStringProperty(mov.getValue().getCategoria()));
	tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
	tableColumnValue.setCellValueFactory(new PropertyValueFactory<>("valor"));
	tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("descricao"));
	atualizarTabela();
    }

    private void atualizarTabela() {
	
	List<Movimentacao> lista = movDB.procurarListaMovimentacao();
	List<FormatoMovimentacao> listaFormatada = new ArrayList<>();
	for (Movimentacao mov : lista) {
	    listaFormatada.add(formato.formatoTabela(mov));

	}
	Collections.reverse(listaFormatada);
	obsLista = FXCollections.observableArrayList(listaFormatada);
	tableViewFlow.setItems(obsLista);
    }

    private void inicializarGraficoPizza() {
	List<PieChart.Data> lista = movDB.graficoReceitasDespesas();
	obsGrafico = FXCollections.observableArrayList(lista);
	pieChart.setData(obsGrafico);
	
    }

    public void inserirDadoTransferidoListener(DadoTransferidoListener listener) {
	dadosModificadosListeners.add(listener);
    }
    
    
    private void notificarDadosModificados() {
	
	for(DadoTransferidoListener listener : dadosModificadosListeners) {
	    listener.onDadoTransferido();
	}
    }

    @Override
    public void onDadoTransferido() {
	inicializarLinhas();
	inicializarTabela();
	inicializarGraficoPizza();
    }
    
    public FXMLLoader atualizandoMainView() {
	String url = "/view/MainView.fxml";
	FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
	return loader;
    }
    
    public FXMLLoader atualizandoLoginView() {
	String url = "/view/FormView.fxml";
	FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
	return loader;
    }
}
