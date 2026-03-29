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
public class StavkaListeZeljaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDArt")
    private int iDArt;

    public StavkaListeZeljaPK() {
    }

    public StavkaListeZeljaPK(int iDKor, int iDArt) {
        this.iDKor = iDKor;
        this.iDArt = iDArt;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
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
        hash += (int) iDKor;
        hash += (int) iDArt;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaListeZeljaPK)) {
            return false;
        }
        StavkaListeZeljaPK other = (StavkaListeZeljaPK) object;
        if (this.iDKor != other.iDKor) {
            return false;
        }
        if (this.iDArt != other.iDArt) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StavkaListeZeljaPK[ iDKor=" + iDKor + ", iDArt=" + iDArt + " ]";
    }
    
}
