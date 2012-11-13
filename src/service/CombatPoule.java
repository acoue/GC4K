package service;

public class CombatPoule {
	
	int idParticipant1;
	int idParticipant2;
	String sResultat;
	String sResultatParticipant1;
	String sResultatParticipant2;
	int idParticipantVainqueur;
	int numeroPoule;
	
	public int getNumeroPoule() {
		return numeroPoule;
	}

	public void setNumeroPoule(int numeroPoule) {
		this.numeroPoule = numeroPoule;
	}

	public CombatPoule(int numPoule, int idParticipant1, int idParticipant2) {
		super();
		this.idParticipant1 = idParticipant1;
		this.idParticipant2 = idParticipant2;
		this.numeroPoule = numPoule;
	}
	
	public int getIdParticipant1() {
		return idParticipant1;
	}
	public void setIdParticipant1(int idParticipant1) {
		this.idParticipant1 = idParticipant1;
	}
	public int getIdParticipant2() {
		return idParticipant2;
	}
	public void setIdParticipant2(int idParticipant2) {
		this.idParticipant2 = idParticipant2;
	}
	public String getResultat() {
		return sResultat;
	}
	public void setResultat(String sResultat) {
		this.sResultat = sResultat;
	}
	public String getResultatParticipant1() {
		return sResultatParticipant1;
	}
	public void setResultatParticipant1(String sResultatParticipant1) {
		this.sResultatParticipant1 = sResultatParticipant1;
	}
	public String getResultatParticipant2() {
		return sResultatParticipant2;
	}
	public void setResultatParticipant2(String sResultatParticipant2) {
		this.sResultatParticipant2 = sResultatParticipant2;
	}
	public int getIdParticipantVainqueur() {
		return idParticipantVainqueur;
	}
	public void setIdParticipantVainqueur(int idParticipantVainqueur) {
		this.idParticipantVainqueur = idParticipantVainqueur;
	}

	
	
}
