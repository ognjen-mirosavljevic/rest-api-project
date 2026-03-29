/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "korpa")
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIDKor", query = "SELECT k FROM Korpa k WHERE k.iDKor = :iDKor"),
    @NamedQuery(name = "Korpa.findByCena", query = "SELECT k FROM Korpa k WHERE k.cena = :cena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private Integer iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;
    @OneToMany(mappedBy = "korpa")
    private Collection<StavkaKorpa> stavkaKorpaCollection;

    public Korpa() {
    }

    public Korpa(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Korpa(Integer iDKor, double cena) {
        this.iDKor = iDKor;
        this.cena = cena;
    }

    public Integer getIDKor() {
        return iDKor;
    }

    public void setIDKor(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Collection<StavkaKorpa> getStavkaKorpaCollection() {
        return stavkaKorpaCollection;
    }

    public void setStavkaKorpaCollection(Collection<StavkaKorpa> stavkaKorpaCollection) {
        this.stavkaKorpaCollection = stavkaKorpaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKor != null ? iDKor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korpa[ iDKor=" + iDKor + " ]";
    }
    
}
