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
@Table(name="club")
public class Club implements Serializable {

	@Id
	@Column(name="cod")
	private Integer cod;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="seu")
	private String seu;
	
	@Column(name="num")
	private int num;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_gru") //FK
	private Grup grup;
	
	public Club() {}

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

	public String getSeu() {
		return seu;
	}

	public void setSeu(String seu) {
		this.seu = seu;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Grup getGrup() {
		return grup;
	}

	public void setGrup(Grup grup) {
		this.grup = grup;
	}

	@Override
	public String toString() {
		String msj="";
		msj = "CLUB "+nom+" (codi "+cod+"):\n";
		msj += "Seu: "+seu+". Num: "+num+"\n";
		msj += "Pertany al grup "+grup.getNom()+"\n";
		return msj;
	}
	
	
}
