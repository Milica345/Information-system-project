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
public class KorpaSadrziPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idKorpe")
    private int idKorpe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArtikla")
    private int idArtikla;

    public KorpaSadrziPK() {
    }

    public KorpaSadrziPK(int idKorpe, int idArtikla) {
        this.idKorpe = idKorpe;
        this.idArtikla = idArtikla;
    }

    public int getIdKorpe() {
        return idKorpe;
    }

    public void setIdKorpe(int idKorpe) {
        this.idKorpe = idKorpe;
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
        hash += (int) idKorpe;
        hash += (int) idArtikla;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KorpaSadrziPK)) {
            return false;
        }
        KorpaSadrziPK other = (KorpaSadrziPK) object;
        if (this.idKorpe != other.idKorpe) {
            return false;
        }
        if (this.idArtikla != other.idArtikla) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KorpaSadrziPK[ idKorpe=" + idKorpe + ", idArtikla=" + idArtikla + " ]";
    }
    
}
