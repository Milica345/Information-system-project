/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author student
 */
@Entity
@Table(name = "listaZelja")
@NamedQueries({
    @NamedQuery(name = "ListaZelja.findAll", query = "SELECT l FROM ListaZelja l"),
    @NamedQuery(name = "ListaZelja.findByIdListe", query = "SELECT l FROM ListaZelja l WHERE l.idListe = :idListe"),
    @NamedQuery(name = "ListaZelja.findByDatumKreiranja", query = "SELECT l FROM ListaZelja l WHERE l.datumKreiranja = :datumKreiranja")})
public class ListaZelja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idListe")
    private Integer idListe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datumKreiranja")
    @Temporal(TemporalType.DATE)
    private Date datumKreiranja;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika")
    @OneToOne(optional = false)
    private Korisnik idKorisnika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listaZelja")
    private List<NaListi> naListiList;

    public ListaZelja() {
    }

    public ListaZelja(Integer idListe) {
        this.idListe = idListe;
    }

    public ListaZelja(Integer idListe, Date datumKreiranja) {
        this.idListe = idListe;
        this.datumKreiranja = datumKreiranja;
    }

    public Integer getIdListe() {
        return idListe;
    }

    public void setIdListe(Integer idListe) {
        this.idListe = idListe;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public List<NaListi> getNaListiList() {
        return naListiList;
    }

    public void setNaListiList(List<NaListi> naListiList) {
        this.naListiList = naListiList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idListe != null ? idListe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaZelja)) {
            return false;
        }
        ListaZelja other = (ListaZelja) object;
        if ((this.idListe == null && other.idListe != null) || (this.idListe != null && !this.idListe.equals(other.idListe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ListaZelja[ idListe=" + idListe + " ]";
    }
    
}
