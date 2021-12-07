package com.example.novigrad;

public class SearchDemande extends Demande{

    private String address;
    private int[] times;

    public SearchDemande(String address, String firstName, String lastName, String nomDeUtilisateur, String nomDuServiceDemande, String nomSuccursaleDemande, int[] times) {
        super(firstName, lastName, nomDeUtilisateur, nomDuServiceDemande, nomSuccursaleDemande, 0);
        this.address = address;
        this.times = times.clone();
    }

    public Demande compress() {
        return new Demande(this.getFirstName(), this.getLastName(),
                this.getNomDeUtilisateur(), this.getNomDuServiceDemande(), this.getNomSuccursaleDemande(), 0);
    }

    public void setAddress(String ad) {
        address = ad;
    }

    public String getAddress() {
        return address;
    }

    public int[] getTimes() {
        return times;
    }

}
