/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "lista_zelja")
@NamedQueries({
    @NamedQuery(name = "ListaZelja.findAll", query = "SELECT l FROM ListaZelja l"),
    @NamedQuery(name = "ListaZelja.findByIDKor", query = "SELECT l FROM ListaZelja l WHERE l.iDKor = :iDKor"),
    @NamedQuery(name = "ListaZelja.findByDatum", query = "SELECT l FROM ListaZelja l WHERE l.datum = :datum")})
public class ListaZelja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private Integer iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @OneToMany(mappedBy = "listaZelja")
    private Collection<StavkaListeZelja> stavkaListeZeljaCollection;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;

    public ListaZelja() {
    }

    public ListaZelja(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public ListaZelja(Integer iDKor, Date datum) {
        this.iDKor = iDKor;
        this.datum = datum;
    }

    public Integer getIDKor() {
        return iDKor;
    }

    public void setIDKor(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Collection<StavkaListeZelja> getStavkaListeZeljaCollection() {
        return stavkaListeZeljaCollection;
    }

    public void setStavkaListeZeljaCollection(Collection<StavkaListeZelja> stavkaListeZeljaCollection) {
        this.stavkaListeZeljaCollection = stavkaListeZeljaCollection;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
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
        if (!(object instanceof ListaZelja)) {
            return false;
        }
        ListaZelja other = (ListaZelja) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ListaZelja[ iDKor=" + iDKor + " ]";
    }
    
}
