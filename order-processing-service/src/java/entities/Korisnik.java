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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "korisnik")
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIDKor", query = "SELECT k FROM Korisnik k WHERE k.iDKor = :iDKor")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private Integer iDKor;
    @OneToMany(mappedBy = "iDKor")
    private Collection<Narudzbina> narudzbinaCollection;

    public Korisnik() {
    }

    public Korisnik(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Integer getIDKor() {
        return iDKor;
    }

    public void setIDKor(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Collection<Narudzbina> getNarudzbinaCollection() {
        return narudzbinaCollection;
    }

    public void setNarudzbinaCollection(Collection<Narudzbina> narudzbinaCollection) {
        this.narudzbinaCollection = narudzbinaCollection;
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
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Korisnik[ iDKor=" + iDKor + " ]";
    }
    
}
