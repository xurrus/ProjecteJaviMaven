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
@Table(name="canço")
public class Canso implements Serializable {

	@Id
	@Column(name="cod")
	private Integer cod;
	
	@Column(name="titol")
	private String titol;
	
	@Column(name="duracio")
	private Double duracio;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="canso") //esta.canso
	private List<Esta> llistaDiscs = new ArrayList<Esta>();
	
	public Canso() {}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public Double getDuracio() {
		return duracio;
	}

	public void setDuracio(Double duracio) {
		this.duracio = duracio;
	}

	public List<Esta> getLlistaDiscs() {
		return llistaDiscs;
	}

	public void setLlistaDiscs(List<Esta> llistaCansonsDisc) {
		this.llistaDiscs = llistaCansonsDisc;
	}

	@Override
	public String toString() {
		String msj="";
		msj = "CANSO "+titol+" (codi "+cod+"):\n";
		msj += "Duracio: "+duracio+"\n";
		msj += "Esta dins dels següents discs:\n";
		for(Esta d: this.llistaDiscs) {
			msj += "\t"+d.getDisc().getNom()+" (codi "+d.getDisc().getCod()+")\n";
		}
		msj += "\n";
		return msj;
	}
	
	
}
