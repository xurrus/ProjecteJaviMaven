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
@Table(name="companyia")
public class Companyia implements Serializable {

	@Id
	@Column(name="cod")
	private Integer cod;
	@Column(name="nom")
	private String nom;
	@Column(name="dir")
	private String dir;
	@Column(name="fax")
	private String fax;
	@Column(name="tfno")
	private String tfno;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="companyia") //Disc.companyia
	private List<Disc> llistaDiscs = new ArrayList<Disc>();
	
	public Companyia() {}

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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTfno() {
		return tfno;
	}

	public void setTfno(String tfno) {
		this.tfno = tfno;
	}

	public List<Disc> getLlistaDiscs() {
		return llistaDiscs;
	}

	public void setLlistaDiscs(List<Disc> llista) {
		this.llistaDiscs = llista;
	}

	@Override
	public String toString() {
		String msj="";
		msj = "COMPANYIA "+nom+" (codi "+cod+"):\n";
		msj += "Direccio: "+dir+". Fax: "+fax+". Telefon: "+tfno+"\n";
		msj += "Conte els següents discs:\n";
		for(Disc d: this.llistaDiscs) {
			msj += "\t"+d.getNom()+" (codi "+d.getCod()+")\n";
		}
		msj += "\n";
		return msj;
	}
	
	
}
