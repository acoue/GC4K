package service;

public class Club {
	
	private int id;
	private String libelle;
	private String numeroAgrement;
	private String adresse;
	private String codePostal;
	private String ville;
	private String telephone;
	private String email;
	
	public Club(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getNumeroAgrement() {
		return this.numeroAgrement;
	}

	public void setNumeroAgrement(String numeroAgrement) {
		this.numeroAgrement = numeroAgrement;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
