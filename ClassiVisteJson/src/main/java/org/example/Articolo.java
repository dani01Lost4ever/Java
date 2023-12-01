package org.example;

public class Articolo {
    private int ArticoloID;
    private String Nome;
    private String Descrizione;
    private double PrezzoUnitarioVendita;
    private int AliquotaIVA;
    private int Giacenza;

    public Articolo(int articoloID, String nome, String descrizione, double prezzoUnitarioVendita, int aliquotaIVA, int giacenza) {
        ArticoloID = articoloID;
        Nome = nome;
        Descrizione = descrizione;
        PrezzoUnitarioVendita = prezzoUnitarioVendita;
        AliquotaIVA = aliquotaIVA;
        Giacenza = giacenza;
    }

    public int getArticoloID() {
        return ArticoloID;
    }

    public void setArticoloID(int articoloID) {
        ArticoloID = articoloID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    @Override
    public String toString() {
        return "Articolo{" +
                "ArticoloID=" + ArticoloID +
                ", Nome='" + Nome + '\'' +
                ", Descrizione='" + Descrizione + '\'' +
                ", PrezzoUnitarioVendita=" + PrezzoUnitarioVendita +
                ", AliquotaIVA=" + AliquotaIVA +
                ", Giacenza=" + Giacenza +
                '}';
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public double getPrezzoUnitarioVendita() {
        return PrezzoUnitarioVendita;
    }
    public double setPrezzoUnitarioVendita(double prezzoUnitarioVendita) {
        return PrezzoUnitarioVendita = prezzoUnitarioVendita;
    }
    public int getAliquotaIVA() {
        return AliquotaIVA;
    }
    public int setAliquotaIVA(int aliquotaIVA) {
        return AliquotaIVA = aliquotaIVA;
    }
    public int getGiacenza() {
        return Giacenza;
    }
    public int setGiacenza(int giacenza) {
        return Giacenza = giacenza;
    }
}
