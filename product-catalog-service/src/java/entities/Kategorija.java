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
@Table(name = "kategorija")
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIDKat", query = "SELECT k FROM Kategorija k WHERE k.iDKat = :iDKat"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDKat")
    private Integer iDKat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(mappedBy = "iDKat")
    private Collection<Artikal> artikalCollection;
    @OneToMany(mappedBy = "iDKatNat")
    private Collection<Kategorija> kategorijaCollection;
    @JoinColumn(name = "IDKatNat", referencedColumnName = "IDKat")
    @ManyToOne
    private Kategorija iDKatNat;

    public Kategorija() {
    }

    public Kategorija(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public Kategorija(Integer iDKat, String naziv) {
        this.iDKat = iDKat;
        this.naziv = naziv;
    }

    public Integer getIDKat() {
        return iDKat;
    }

    public void setIDKat(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Collection<Artikal> getArtikalCollection() {
        return artikalCollection;
    }

    public void setArtikalCollection(Collection<Artikal> artikalCollection) {
        this.artikalCollection = artikalCollection;
    }

    public Collection<Kategorija> getKategorijaCollection() {
        return kategorijaCollection;
    }

    public void setKategorijaCollection(Collection<Kategorija> kategorijaCollection) {
        this.kategorijaCollection = kategorijaCollection;
    }

    public Kategorija getIDKatNat() {
        return iDKatNat;
    }

    public void setIDKatNat(Kategorija iDKatNat) {
        this.iDKatNat = iDKatNat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKat != null ? iDKat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.iDKat == null && other.iDKat != null) || (this.iDKat != null && !this.iDKat.equals(other.iDKat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kategorija[ iDKat=" + iDKat + " ]";
    }
    
}
