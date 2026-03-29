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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ognje
 */
@Entity
@Table(name = "narudzbina")
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIDNar", query = "SELECT n FROM Narudzbina n WHERE n.iDNar = :iDNar"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVreme", query = "SELECT n FROM Narudzbina n WHERE n.vreme = :vreme"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDNar")
    private Integer iDNar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupna_cena")
    private double ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "adresa")
    private String adresa;
    @JoinColumn(name = "IDGra", referencedColumnName = "IDGra")
    @ManyToOne(optional = false)
    private Grad iDGra;
    @JoinColumn(name = "IDKor", referencedColumnName = "IDKor")
    @ManyToOne(optional = false)
    private Korisnik iDKor;
    @OneToMany(mappedBy = "narudzbina")
    private Collection<StavkaNarudzbina> stavkaNarudzbinaCollection;
    @OneToOne(mappedBy = "iDNar")
    private Transakcija transakcija;

    public Narudzbina() {
    }

    public Narudzbina(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public Narudzbina(Integer iDNar, double ukupnaCena, Date vreme, String adresa) {
        this.iDNar = iDNar;
        this.ukupnaCena = ukupnaCena;
        this.vreme = vreme;
        this.adresa = adresa;
    }

    public Integer getIDNar() {
        return iDNar;
    }

    public void setIDNar(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Grad getIDGra() {
        return iDGra;
    }

    public void setIDGra(Grad iDGra) {
        this.iDGra = iDGra;
    }

    public Korisnik getIDKor() {
        return iDKor;
    }

    public void setIDKor(Korisnik iDKor) {
        this.iDKor = iDKor;
    }

    public Collection<StavkaNarudzbina> getStavkaNarudzbinaCollection() {
        return stavkaNarudzbinaCollection;
    }

    public void setStavkaNarudzbinaCollection(Collection<StavkaNarudzbina> stavkaNarudzbinaCollection) {
        this.stavkaNarudzbinaCollection = stavkaNarudzbinaCollection;
    }

    public Transakcija getTransakcijaCollection() {
        return transakcija;
    }

    public void setTransakcijaCollection(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNar != null ? iDNar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.iDNar == null && other.iDNar != null) || (this.iDNar != null && !this.iDNar.equals(other.iDNar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Narudzbina[ iDNar=" + iDNar + " ]";
    }
    
}
