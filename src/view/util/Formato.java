package view.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import model.entities.Movimentacao;

public class Formato {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date data = Date.from(Instant.now());
    
    public String retornaMesAno() {
   	int mesEmNumero = converterMes();
   	String ano = converterAno();
   	String mesEmTexto = mesEmTexto(mesEmNumero);
   	return "MÊS ATUAL - " + mesEmTexto + " " + ano;
       }
    
    public String formatoMoeda(Double valor) {
	DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
	simbolo.setDecimalSeparator(',');
	simbolo.setGroupingSeparator('.');
	
	DecimalFormat formato = new DecimalFormat("#,###.00", simbolo);
	return formato.format(valor);
    }
    
    public String formatoDiaMesAno(Date data) {
	return sdf.format(data);
    }
    
    private Integer converterMes() {
   	return Integer.parseInt(sdf.format(data).substring(3, 5));
       }
       
    private String converterAno() {
   	return sdf.format(data).substring(6, 10);
       }
  
    private String mesEmTexto(int mes) {
   	return mes == 1  ?  "Janeiro"   :
   	       mes == 2  ?  "Fevereiro" :
   	       mes == 3  ?  "Março"     :
   	       mes == 4  ?  "Abril"     :
   	       mes == 5  ?  "Maio"      :
   	       mes == 6  ?  "Junho"     :
   	       mes == 7  ?  "Julho"     :
   	       mes == 8  ?  "Agosto"    :
   	       mes == 9  ?  "Setembro"  :
   	       mes == 10 ?  "Outubro"   :
   	       mes == 11 ?  "Novembro"  :
   	       mes == 12 ?  "Dezembro"  :
   	       "";				       
       }
    
    public FormatoMovimentacao formatoTabela(Movimentacao mov) {
	FormatoMovimentacao formatoMovimentacao = new FormatoMovimentacao(mov);
	return formatoMovimentacao;
    }
    
    public String tratandoDadosPagamento(String pagamento) {
	return pagamento == "Sim" ? "S" : 
	       pagamento == "Não" ? "N" : "N";	   
    }
    
    public String tratandoValor(String valor) {
	return valor.replace(',', '.');
    }
    
    public Date getData() {
	return data;
    }
    
    public Date formatoDataBancoDados() {
	SimpleDateFormat sdfBancoDados = new SimpleDateFormat("yyyy-MM-dd");
	try {
	    return sdfBancoDados.parse(data.toString());
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return null;
    }
}
