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
@Table(name = "uloga")
@NamedQueries({
    @NamedQuery(name = "Uloga.findAll", query = "SELECT u FROM Uloga u"),
    @NamedQuery(name = "Uloga.findByIDUlo", query = "SELECT u FROM Uloga u WHERE u.iDUlo = :iDUlo"),
    @NamedQuery(name = "Uloga.findByNaziv", query = "SELECT u FROM Uloga u WHERE u.naziv = :naziv")})
public class Uloga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDUlo")
    private Integer iDUlo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDUlo")
    private Collection<Korisnik> korisnikCollection;

    public Uloga() {
    }

    public Uloga(Integer iDUlo) {
        this.iDUlo = iDUlo;
    }

    public Uloga(Integer iDUlo, String naziv) {
        this.iDUlo = iDUlo;
        this.naziv = naziv;
    }

    public Integer getIDUlo() {
        return iDUlo;
    }

    public void setIDUlo(Integer iDUlo) {
        this.iDUlo = iDUlo;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Collection<Korisnik> getKorisnikCollection() {
        return korisnikCollection;
    }

    public void setKorisnikCollection(Collection<Korisnik> korisnikCollection) {
        this.korisnikCollection = korisnikCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDUlo != null ? iDUlo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uloga)) {
            return false;
        }
        Uloga other = (Uloga) object;
        if ((this.iDUlo == null && other.iDUlo != null) || (this.iDUlo != null && !this.iDUlo.equals(other.iDUlo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Uloga[ iDUlo=" + iDUlo + " ]";
    }
    
}
