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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "artikal")
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIDArt", query = "SELECT a FROM Artikal a WHERE a.iDArt = :iDArt"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopust", query = "SELECT a FROM Artikal a WHERE a.popust = :popust")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDArt")
    private Integer iDArt;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "popust")
    private int popust;
    @OneToMany(mappedBy = "artikal")
    private Collection<StavkaKorpa> stavkaKorpaCollection;
    @JoinColumn(name = "IDKat", referencedColumnName = "IDKat")
    @ManyToOne(optional = false)
    private Kategorija iDKat;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @OneToMany(mappedBy = "artikal")
    private Collection<StavkaListeZelja> stavkaListeZeljaCollection;

    public Artikal() {
    }

    public Artikal(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Artikal(Integer iDArt, String naziv, String opis, double cena, int popust) {
        this.iDArt = iDArt;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.popust = popust;
    }

    public Integer getIDArt() {
        return iDArt;
    }

    public void setIDArt(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public Collection<StavkaKorpa> getStavkaKorpaCollection() {
        return stavkaKorpaCollection;
    }

    public void setStavkaKorpaCollection(Collection<StavkaKorpa> stavkaKorpaCollection) {
        this.stavkaKorpaCollection = stavkaKorpaCollection;
    }

    public Kategorija getIDKat() {
        return iDKat;
    }

    public void setIDKat(Kategorija iDKat) {
        this.iDKat = iDKat;
    }

    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    public Collection<StavkaListeZelja> getStavkaListeZeljaCollection() {
        return stavkaListeZeljaCollection;
    }

    public void setStavkaListeZeljaCollection(Collection<StavkaListeZelja> stavkaListeZeljaCollection) {
        this.stavkaListeZeljaCollection = stavkaListeZeljaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDArt != null ? iDArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.iDArt == null && other.iDArt != null) || (this.iDArt != null && !this.iDArt.equals(other.iDArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Artikal[ iDArt=" + iDArt + " ]";
    }
    
}
