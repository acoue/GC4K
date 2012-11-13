package service;

import java.util.Date;

public class Participant {
	
	private int id;
	private String nom;
	private String prenom;
	private int idClub;
	private String club;
	private String numeroLicence;
	private String dateNaissance;
	private int passeportOk;
	private String dateCertificat;
	
	public Participant(int id, String nom, String prenom, int idClub) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.idClub = idClub;
	}

	public Participant(int id, String nom, String prenom, String club) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.club = club;
	}
	
	public Participant(int id) {
		super();
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getIdClub() {
		return this.idClub;
	}

	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}

	public String getClub() {
		return this.club;
	}

	public void setClub(String club) {
		this.club = club;
	}
	
	public String getIdentite() {
		return this.id + " - " + this.prenom + " " + this.nom + " - " + this.idClub;
	}

	public String getNumeroLicence() {
		return this.numeroLicence;
	}

	public void setNumeroLicence(String numeroLicence) {
		this.numeroLicence = numeroLicence;
	}

	public String getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public int getPasseportOk() {
		return this.passeportOk;
	}

	public void setPasseportOk(int passeportOk) {
		this.passeportOk = passeportOk;
	}

	public String getDateCertificat() {
		return this.dateCertificat;
	}

	public void setDateCertificat(String dateCertificat) {
		this.dateCertificat = dateCertificat;
	}
	
}
