package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="disc")
public class Disc implements Serializable {

	@Id
	@Column(name="cod")
	private Integer cod;
	@Column(name="nom")
	private String nom;
	@Column(name="dia")
	private String dia;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_comp") //FK
	private Companyia companyia;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_gru") //FK
	private Grup grup;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="disc") //Esta.disc
	private List<Esta> llistaCansons = new ArrayList<Esta>();
	
	public Disc() {}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Companyia getCompanyia() {
		return companyia;
	}

	public void setCompanyia(Companyia companyia) {
		this.companyia = companyia;
	}

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	public List<Esta> getLlistaCansons() {
		return llistaCansons;
	}

	public void setLlistaCansons(List<Esta> llistaCansons) {
		this.llistaCansons = llistaCansons;
	}

	@Override
	public String toString() {
		String msj="";
		msj = "DISC "+nom+" (codi "+cod+"):\n";
		msj += "Dia: "+dia+"\n";
		msj += "Te les següents cansons:\n";
		for(Esta c: this.llistaCansons) {
			msj += "\t"+c.getCanso().getTitol()+" (codi "+c.getCanso().getCod()+")\n";
		}
		msj += "Disc fet per el grup: "+grup.getNom()+"\n";
		msj += "Pertany a la companyia "+companyia.getNom();
		msj += "\n";
		return msj;
	}
	
	
}
