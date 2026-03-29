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
public class StavkaKorpaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDArt")
    private int iDArt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;

    public StavkaKorpaPK() {
    }

    public StavkaKorpaPK(int iDArt, int iDKor) {
        this.iDArt = iDArt;
        this.iDKor = iDKor;
    }

    public int getIDArt() {
        return iDArt;
    }

    public void setIDArt(int iDArt) {
        this.iDArt = iDArt;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDArt;
        hash += (int) iDKor;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StavkaKorpaPK)) {
            return false;
        }
        StavkaKorpaPK other = (StavkaKorpaPK) object;
        if (this.iDArt != other.iDArt) {
            return false;
        }
        if (this.iDKor != other.iDKor) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StavkaKorpaPK[ iDArt=" + iDArt + ", iDKor=" + iDKor + " ]";
    }
    
}
