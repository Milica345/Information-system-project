/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author student
 */
@Entity
@Table(name = "naListi")
@NamedQueries({
    @NamedQuery(name = "NaListi.findAll", query = "SELECT n FROM NaListi n"),
    @NamedQuery(name = "NaListi.findByIdListe", query = "SELECT n FROM NaListi n WHERE n.naListiPK.idListe = :idListe"),
    @NamedQuery(name = "NaListi.findByIdArtikla", query = "SELECT n FROM NaListi n WHERE n.naListiPK.idArtikla = :idArtikla"),
    @NamedQuery(name = "NaListi.findByVremeDodavanja", query = "SELECT n FROM NaListi n WHERE n.vremeDodavanja = :vremeDodavanja")})
public class NaListi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NaListiPK naListiPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vremeDodavanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeDodavanja;
    @JoinColumn(name = "idArtikla", referencedColumnName = "idArtikla", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idListe", referencedColumnName = "idListe", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ListaZelja listaZelja;

    public NaListi() {
    }

    public NaListi(NaListiPK naListiPK) {
        this.naListiPK = naListiPK;
    }

    public NaListi(NaListiPK naListiPK, Date vremeDodavanja) {
        this.naListiPK = naListiPK;
        this.vremeDodavanja = vremeDodavanja;
    }

    public NaListi(int idListe, int idArtikla) {
        this.naListiPK = new NaListiPK(idListe, idArtikla);
    }

    public NaListiPK getNaListiPK() {
        return naListiPK;
    }

    public void setNaListiPK(NaListiPK naListiPK) {
        this.naListiPK = naListiPK;
    }

    public Date getVremeDodavanja() {
        return vremeDodavanja;
    }

    public void setVremeDodavanja(Date vremeDodavanja) {
        this.vremeDodavanja = vremeDodavanja;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public ListaZelja getListaZelja() {
        return listaZelja;
    }

    public void setListaZelja(ListaZelja listaZelja) {
        this.listaZelja = listaZelja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (naListiPK != null ? naListiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NaListi)) {
            return false;
        }
        NaListi other = (NaListi) object;
        if ((this.naListiPK == null && other.naListiPK != null) || (this.naListiPK != null && !this.naListiPK.equals(other.naListiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.NaListi[ naListiPK=" + naListiPK + " ]";
    }
    
}
