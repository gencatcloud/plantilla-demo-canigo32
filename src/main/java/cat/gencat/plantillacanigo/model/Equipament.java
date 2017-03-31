package cat.gencat.plantillacanigo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "equipaments")
public class Equipament {

	private Long id;
	
	private String nom;
	
	private String municipi;
	
	public Equipament() {
		
	}
	
	public Equipament(Long id) {
		this.id =id;
	}
	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "increment")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Column(name = "nom", nullable = false, unique = true)
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "municipi")
	public String getMunicipi() {
		return municipi;
	}

	public void setMunicipi(String municipi) {
		this.municipi = municipi;
	}	
	
	@Override
	public String toString() {
		return "Equipament [nom=" + nom + "]";
	}
	
}
