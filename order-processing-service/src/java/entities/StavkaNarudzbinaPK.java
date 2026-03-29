/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ognje
 */
@Embeddable
public class StavkaNarudzbinaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDNar")
    private int iDNar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDArt")
    private int iDArt;

    public StavkaNarudzbinaPK() {
    }

    public StavkaNarudzbinaPK(int iDNar, int iDArt) {
        this.iDNar = iDNar;
        this.iDArt = iDArt;
    }

    public int getIDNar() {
        return iDNar;
    }

    public void setIDNar(int iDNar) {
        this.iDNar = iDNar;
    }

    public int getIDArt() {
        return iDArt;
    }

    public void setIDArt(int iDArt) {
        this.iDArt = iDArt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDNar;
        hash += (int) iDArt;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaNarudzbinaPK)) {
            return false;
        }
        StavkaNarudzbinaPK other = (StavkaNarudzbinaPK) object;
        if (this.iDNar != other.iDNar) {
            return false;
        }
        if (this.iDArt != other.iDArt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StavkaNarudzbinaPK[ iDNar=" + iDNar + ", iDArt=" + iDArt + " ]";
    }
    
}
