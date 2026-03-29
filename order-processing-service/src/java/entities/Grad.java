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
@Table(name = "grad")
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g"),
    @NamedQuery(name = "Grad.findByIDGra", query = "SELECT g FROM Grad g WHERE g.iDGra = :iDGra")})
public class Grad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDGra")
    private Integer iDGra;
    @OneToMany(mappedBy = "iDGra")
    private Collection<Narudzbina> narudzbinaCollection;

    public Grad() {
    }

    public Grad(Integer iDGra) {
        this.iDGra = iDGra;
    }

    public Integer getIDGra() {
        return iDGra;
    }

    public void setIDGra(Integer iDGra) {
        this.iDGra = iDGra;
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
        hash += (iDGra != null ? iDGra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.iDGra == null && other.iDGra != null) || (this.iDGra != null && !this.iDGra.equals(other.iDGra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grad[ iDGra=" + iDGra + " ]";
    }
    
}
