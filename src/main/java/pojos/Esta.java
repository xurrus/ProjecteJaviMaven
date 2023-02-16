package pojos;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="esta")
public class Esta implements Serializable {

	@Id
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "can") //FK
	private Canso canso;
	
	@Id
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cod") //FK
	private Disc disc;
	
	public Esta() {}

	public Canso getCanso() {
		return canso;
	}

	public void setCanso(Canso canso) {
		this.canso = canso;
	}

	public Disc getDisc() {
		return disc;
	}

	public void setDisc(Disc disc) {
		this.disc = disc;
	}

	@Override
	public String toString() {
		return "Esta [canso=" + canso + ", disc=" + disc + "]";
	}
	
	
}
