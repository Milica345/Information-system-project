/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author student
 */
@Entity
@Table(name = "korpaSadrzi")
@NamedQueries({
    @NamedQuery(name = "KorpaSadrzi.findAll", query = "SELECT k FROM KorpaSadrzi k"),
    @NamedQuery(name = "KorpaSadrzi.findByIdKorpe", query = "SELECT k FROM KorpaSadrzi k WHERE k.korpaSadrziPK.idKorpe = :idKorpe"),
    @NamedQuery(name = "KorpaSadrzi.findByIdArtikla", query = "SELECT k FROM KorpaSadrzi k WHERE k.korpaSadrziPK.idArtikla = :idArtikla"),
    @NamedQuery(name = "KorpaSadrzi.findByKolicina", query = "SELECT k FROM KorpaSadrzi k WHERE k.kolicina = :kolicina")})
public class KorpaSadrzi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KorpaSadrziPK korpaSadrziPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @JoinColumn(name = "idArtikla", referencedColumnName = "idArtikla", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idKorpe", referencedColumnName = "idKorpe", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Korpa korpa;

    public KorpaSadrzi() {
    }

    public KorpaSadrzi(KorpaSadrziPK korpaSadrziPK) {
        this.korpaSadrziPK = korpaSadrziPK;
    }

    public KorpaSadrzi(KorpaSadrziPK korpaSadrziPK, int kolicina) {
        this.korpaSadrziPK = korpaSadrziPK;
        this.kolicina = kolicina;
    }

    public KorpaSadrzi(int idKorpe, int idArtikla) {
        this.korpaSadrziPK = new KorpaSadrziPK(idKorpe, idArtikla);
    }

    public KorpaSadrziPK getKorpaSadrziPK() {
        return korpaSadrziPK;
    }

    public void setKorpaSadrziPK(KorpaSadrziPK korpaSadrziPK) {
        this.korpaSadrziPK = korpaSadrziPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korpaSadrziPK != null ? korpaSadrziPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KorpaSadrzi)) {
            return false;
        }
        KorpaSadrzi other = (KorpaSadrzi) object;
        if ((this.korpaSadrziPK == null && other.korpaSadrziPK != null) || (this.korpaSadrziPK != null && !this.korpaSadrziPK.equals(other.korpaSadrziPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KorpaSadrzi[ korpaSadrziPK=" + korpaSadrziPK + " ]";
    }
    
}
