package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movimentacao")
public class Movimentacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TiposMovimentacao tiposMovimentacao;

    @ManyToOne
    private Categoria categoria;

    private Date data;

    private Double valor;

    private String descricao;

    private Character pago;

    public Movimentacao() {
    }

    public Movimentacao(Long id, Date data, Double valor, String descricao, Character pago) {
	this.id = id;
	this.data = data;
	this.valor = valor;
	this.descricao = descricao;
	this.pago = pago;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public TiposMovimentacao getTiposMovimentacao() {
	return tiposMovimentacao;
    }

    public void setTiposMovimentacao(TiposMovimentacao tiposMovimentacao) {
	this.tiposMovimentacao = tiposMovimentacao;
    }

    public Categoria getCategoria() {
	return categoria;
    }

    public void setCategoria(Categoria categoria) {
	this.categoria = categoria;
    }

    public Date getDate() {
	return data;
    }

    public void setDate(Date date) {
	this.data = date;
    }

    public Double getValor() {
	return valor;
    }

    public void setValor(Double valor) {
	this.valor = valor;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public Character getPago() {
	return pago;
    }

    public void setPago(Character pago) {
	this.pago = pago;
    }

    @Override
    public int hashCode() {
	return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Movimentacao other = (Movimentacao) obj;
	return Objects.equals(id, other.id);
    }
}
