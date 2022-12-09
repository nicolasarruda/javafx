package db;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javafx.scene.chart.PieChart;
import model.entities.Categoria;
import model.entities.Movimentacao;
import model.entities.TiposMovimentacao;
import view.util.Formato;

public class MovimentacaoDB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");

    private CategoriaDB catDB = new CategoriaDB();

    private TiposMovimentacaoDB tiposDB = new TiposMovimentacaoDB();

    private Movimentacao movimentacao;

    public MovimentacaoDB(Movimentacao movimentacao) {
	this.movimentacao = movimentacao;
    }

    public List<Movimentacao> procurarListaMovimentacao() {
	EntityManager em = emf.createEntityManager();
	Query query = em.createNativeQuery("select * from movimentacao", Movimentacao.class);
	List<Movimentacao> listaMovimentacao = query.getResultList();
	return listaMovimentacao;
    }

    public Double calculandoSaldoAtual() {
	double saldoAtual = 0.0;
	for (Movimentacao mov : procurarListaMovimentacao()) {

	    if (mov.getPago() == 'S') {

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Receita")) {
		    saldoAtual += mov.getValor();
		}

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Despesa")) {
		    saldoAtual -= mov.getValor();
		}
	    }
	}
	return saldoAtual;
    }

    public Double calculandoSaldoPrevisto() {
	double saldoPrevisto = calculandoSaldoAtual();
	for (Movimentacao mov : procurarListaMovimentacao()) {

	    if (mov.getPago() == 'N') {

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Receita")) {
		    saldoPrevisto += mov.getValor();
		}

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Despesa")) {
		    saldoPrevisto -= mov.getValor();
		}
	    }
	}
	return saldoPrevisto;
    }

    public Double ultimaMovimentacao() {
	List<Movimentacao> lista = procurarListaMovimentacao();
	int indiceUltimoMovimento = lista.size() - 1;
	Movimentacao ultimaMovimentacao = lista.get(indiceUltimoMovimento);
	return ultimaMovimentacao.getValor();
    }

    public List<PieChart.Data> graficoReceitasDespesas() {
	Formato formato = new Formato();
	List<PieChart.Data> lista = new ArrayList<>();
	double receita = 0.0;
	double despesa = 0.0;
	for (Movimentacao mov : procurarListaMovimentacao()) {

	    if (mov.getPago() == 'S') {

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Receita")) {
		    receita += mov.getValor();
		}

		if (mov.getTiposMovimentacao().getDescricao().equalsIgnoreCase("Despesa")) {
		    despesa += mov.getValor();
		}
	    }

	}
	lista.add(new PieChart.Data("Receita R$" + formato.formatoMoeda(receita), receita));
	lista.add(new PieChart.Data("Despesa R$" + formato.formatoMoeda(despesa), despesa));
	return lista;
    }

    public Movimentacao mostrarObjeto() {
	movimentacao = procurarListaMovimentacao().get(0);
	return movimentacao;
    }

    public void inserirMovimentacao(List<String> dadosFormulario) {
	EntityManagerFactory emfInserir = Persistence.createEntityManagerFactory("db");
	EntityManager em = emfInserir.createEntityManager();

	Long categoriaId = Long.valueOf(dadosFormulario.get(2));
	Categoria categoria = catDB.encontrarCategoria(categoriaId);

	Long tiposId = Long.valueOf(dadosFormulario.get(0));
	TiposMovimentacao tipos = tiposDB.encontrarTiposMovimentacao(tiposId);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = Date.from(Instant.now());
	String data = sdf.format(date);

	double valor = Double.parseDouble(dadosFormulario.get(3));
	char[] pago = dadosFormulario.get(1).toCharArray();
	String descricao = dadosFormulario.get(4);
	Movimentacao movimentacao = new Movimentacao(null, java.sql.Date.valueOf(data), valor, descricao, pago[0]);

	movimentacao.setCategoria(categoria);
	movimentacao.setTiposMovimentacao(tipos);

	em.getTransaction().begin();
	em.persist(movimentacao);
	em.getTransaction().commit();
	em.close();
	emfInserir.close();
    }

    public void deletarLinhaTabela(Long id) {
	EntityManagerFactory emfDeletar = Persistence.createEntityManagerFactory("db");
	EntityManager em = emfDeletar.createEntityManager();

	movimentacao = em.find(Movimentacao.class, id);

	if (movimentacao != null) {
	    em.getTransaction().begin();
	    em.remove(movimentacao);
	    em.getTransaction().commit();
	}

	em.close();
	emfDeletar.close();
    }


}
