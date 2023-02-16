package pojos;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="pertany")
public class Pertany implements Serializable {

	@Id
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "dni") //FK
	private Artista artista;
	
	@Id
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cod") //FK
	private Grup grup;
	
	@Column(name="funcio")
	private String funcio;
	
	public Pertany() {}

	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	public String getFuncio() {
		return funcio;
	}

	public void setFuncio(String funcio) {
		this.funcio = funcio;
	}

	@Override
	public String toString() {
		return "Pertany [artista=" + artista + ", grup=" + grup + ", funcio=" + funcio + "]";
	}
	
	
}
