package pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="grup")
public class Grup implements Serializable {

	@Id
	@Column(name="cod")
	private Integer cod;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="dia")
	private String dia;
	
	@Column(name="pais")
	private String pais;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="grup") //Club.grup
	private List<Club> llistaClubs = new ArrayList<Club>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="grup") //Pertany.grup
	private List<Pertany> llistaPertanys = new ArrayList<Pertany>();
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="grup") //Disc.grup
	private List<Disc> llistaDiscs = new ArrayList<Disc>();
	
	public Grup() {}

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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Club> getLlistaClubs() {
		return llistaClubs;
	}

	public void setLlistaClubs(List<Club> llistaClubs) {
		this.llistaClubs = llistaClubs;
	}

	public List<Pertany> getLlistaPertanys() {
		return llistaPertanys;
	}

	public void setLlistaPertanys(List<Pertany> llistaPertanys) {
		this.llistaPertanys = llistaPertanys;
	}

	public List<Disc> getLlistaDiscs() {
		return llistaDiscs;
	}

	public void setLlistaDiscs(List<Disc> llistaDiscs) {
		this.llistaDiscs = llistaDiscs;
	}

	@Override
	public String toString() {
		String msj="";
		msj = "GRUP "+nom+" (codi "+cod+"):\n";
		msj += "Dia: "+dia+"\n";
		msj += "Compost dels següents artistes:\n";
		for(Pertany p: this.llistaPertanys) {
			msj += "\t"+p.getArtista().getNom()+"\n";
		}
		msj += "Ha fet els següents discs:\n";
		for(Disc d: this.llistaDiscs) {
			msj += "\t"+d.getNom()+" (codi "+d.getCod()+")\n";
		}
		msj += "Toca en els següents clubs:\n";
		for(Club c: this.llistaClubs) {
			msj += "\t"+c.getNom()+" (codi "+c.getCod()+")\n";
		}
		msj += "\n";
		return msj;
	}
	
	
}
