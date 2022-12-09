package view.util;

import model.entities.Movimentacao;

public class FormatoMovimentacao {

    Formato formato = new Formato();
    
    private Long id;
    private String tipo;
    private String categoria;
    private String data;
    private String valor;
    private String descricao;
    
    public FormatoMovimentacao() {
    }
    
    public FormatoMovimentacao(Movimentacao mov) {
	id = mov.getId();
	tipo = mov.getTiposMovimentacao().getDescricao();
	categoria = mov.getCategoria().getDescricao();
	data = formato.formatoDiaMesAno(mov.getDate());
	valor = "R$ " + formato.formatoMoeda(mov.getValor());
	descricao = mov.getDescricao();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
