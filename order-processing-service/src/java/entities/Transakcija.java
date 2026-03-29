/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "transakcija")
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIDTra", query = "SELECT t FROM Transakcija t WHERE t.iDTra = :iDTra"),
    @NamedQuery(name = "Transakcija.findBySuma", query = "SELECT t FROM Transakcija t WHERE t.suma = :suma"),
    @NamedQuery(name = "Transakcija.findByVreme", query = "SELECT t FROM Transakcija t WHERE t.vreme = :vreme")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTra")
    private Integer iDTra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suma")
    private double suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @JoinColumn(name = "IDNar", referencedColumnName = "IDNar")
    @OneToOne(optional = false)
    @JoinColumn(name = "IDNar", referencedColumnName = "IDNar")
    private Narudzbina iDNar;

    public Transakcija() {
    }

    public Transakcija(Integer iDTra) {
        this.iDTra = iDTra;
    }

    public Transakcija(Integer iDTra, double suma, Date vreme) {
        this.iDTra = iDTra;
        this.suma = suma;
        this.vreme = vreme;
    }

    public Integer getIDTra() {
        return iDTra;
    }

    public void setIDTra(Integer iDTra) {
        this.iDTra = iDTra;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public Narudzbina getIDNar() {
        return iDNar;
    }

    public void setIDNar(Narudzbina iDNar) {
        this.iDNar = iDNar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTra != null ? iDTra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.iDTra == null && other.iDTra != null) || (this.iDTra != null && !this.iDTra.equals(other.iDTra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcija[ iDTra=" + iDTra + " ]";
    }
    
}
