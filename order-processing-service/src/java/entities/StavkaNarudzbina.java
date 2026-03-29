/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "stavka_narudzbina")
@NamedQueries({
    @NamedQuery(name = "StavkaNarudzbina.findAll", query = "SELECT s FROM StavkaNarudzbina s"),
    @NamedQuery(name = "StavkaNarudzbina.findByIDNar", query = "SELECT s FROM StavkaNarudzbina s WHERE s.stavkaNarudzbinaPK.iDNar = :iDNar"),
    @NamedQuery(name = "StavkaNarudzbina.findByIDArt", query = "SELECT s FROM StavkaNarudzbina s WHERE s.stavkaNarudzbinaPK.iDArt = :iDArt"),
    @NamedQuery(name = "StavkaNarudzbina.findByKolicina", query = "SELECT s FROM StavkaNarudzbina s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "StavkaNarudzbina.findByJedinicnaCena", query = "SELECT s FROM StavkaNarudzbina s WHERE s.jedinicnaCena = :jedinicnaCena")})
public class StavkaNarudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StavkaNarudzbinaPK stavkaNarudzbinaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "jedinicnaCena")
    private double jedinicnaCena;
    @JoinColumn(name = "IDArt", referencedColumnName = "IDArt", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "IDNar", referencedColumnName = "IDNar", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Narudzbina narudzbina;

    public StavkaNarudzbina() {
    }

    public StavkaNarudzbina(StavkaNarudzbinaPK stavkaNarudzbinaPK) {
        this.stavkaNarudzbinaPK = stavkaNarudzbinaPK;
    }

    public StavkaNarudzbina(StavkaNarudzbinaPK stavkaNarudzbinaPK, int kolicina, double jedinicnaCena) {
        this.stavkaNarudzbinaPK = stavkaNarudzbinaPK;
        this.kolicina = kolicina;
        this.jedinicnaCena = jedinicnaCena;
    }

    public StavkaNarudzbina(int iDNar, int iDArt) {
        this.stavkaNarudzbinaPK = new StavkaNarudzbinaPK(iDNar, iDArt);
    }

    public StavkaNarudzbinaPK getStavkaNarudzbinaPK() {
        return stavkaNarudzbinaPK;
    }

    public void setStavkaNarudzbinaPK(StavkaNarudzbinaPK stavkaNarudzbinaPK) {
        this.stavkaNarudzbinaPK = stavkaNarudzbinaPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(double jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Narudzbina getNarudzbina() {
        return narudzbina;
    }

    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaNarudzbinaPK != null ? stavkaNarudzbinaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaNarudzbina)) {
            return false;
        }
        StavkaNarudzbina other = (StavkaNarudzbina) object;
        if ((this.stavkaNarudzbinaPK == null && other.stavkaNarudzbinaPK != null) || (this.stavkaNarudzbinaPK != null && !this.stavkaNarudzbinaPK.equals(other.stavkaNarudzbinaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StavkaNarudzbina[ stavkaNarudzbinaPK=" + stavkaNarudzbinaPK + " ]";
    }
    
}
