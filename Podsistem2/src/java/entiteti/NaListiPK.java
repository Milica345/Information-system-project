/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author student
 */
@Embeddable
public class NaListiPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idListe")
    private int idListe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArtikla")
    private int idArtikla;

    public NaListiPK() {
    }

    public NaListiPK(int idListe, int idArtikla) {
        this.idListe = idListe;
        this.idArtikla = idArtikla;
    }

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idListe;
        hash += (int) idArtikla;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NaListiPK)) {
            return false;
        }
        NaListiPK other = (NaListiPK) object;
        if (this.idListe != other.idListe) {
            return false;
        }
        if (this.idArtikla != other.idArtikla) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.NaListiPK[ idListe=" + idListe + ", idArtikla=" + idArtikla + " ]";
    }
    
}
