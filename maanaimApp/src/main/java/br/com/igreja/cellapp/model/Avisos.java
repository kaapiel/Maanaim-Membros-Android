package br.com.igreja.cellapp.model;

import java.io.Serializable;

public class Avisos implements Serializable{

	private static final long serialVersionUID = -3580551079882619913L;
	private Integer id;
	private String imagemEvento;
	private String titulo;
	private String dataEvento;
	private String dataPublicacao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImagemEvento() {
		return imagemEvento;
	}
	public void setImagemEvento(String imagemEvento) {
		this.imagemEvento = imagemEvento;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}
	public String getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(String dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	
}
