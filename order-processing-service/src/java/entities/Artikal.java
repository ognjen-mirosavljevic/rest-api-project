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
@Table(name = "artikal")
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIDArt", query = "SELECT a FROM Artikal a WHERE a.iDArt = :iDArt")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDArt")
    private Integer iDArt;
    @OneToMany(mappedBy = "artikal")
    private Collection<StavkaNarudzbina> stavkaNarudzbinaCollection;

    public Artikal() {
    }

    public Artikal(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Integer getIDArt() {
        return iDArt;
    }

    public void setIDArt(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Collection<StavkaNarudzbina> getStavkaNarudzbinaCollection() {
        return stavkaNarudzbinaCollection;
    }

    public void setStavkaNarudzbinaCollection(Collection<StavkaNarudzbina> stavkaNarudzbinaCollection) {
        this.stavkaNarudzbinaCollection = stavkaNarudzbinaCollection;
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
