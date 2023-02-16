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
@Table(name="artista")
public class Artista implements Serializable {

	@Id
	@Column(name="dni")
	private String dni;

	@Column(name="nom")
	private String nom;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,
	mappedBy="artista") //Pertany.artista
	private List<Pertany> llistaPertanys = new ArrayList<Pertany>();
	
	
	public Artista() {}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Pertany> getLlistaPertanys() {
		return llistaPertanys;
	}

	public void setLlistaPertanys(List<Pertany> llistaPertanys) {
		this.llistaPertanys = llistaPertanys;
	}
	
	@Override
	public String toString() {
		String msj="";
		msj = "ARTISTA "+nom+" (DNI: "+dni+"):\n";
		msj += "Pertany als següents grups:\n";
		for(Pertany p : this.llistaPertanys) {
			msj += "\t"+p.getGrup().getNom()+". Funcio: "+p.getFuncio()+"\n";
		}
		msj += "\n";
		return msj;
	}
}
