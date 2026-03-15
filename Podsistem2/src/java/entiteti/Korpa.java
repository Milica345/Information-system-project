/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author student
 */
@Entity
@Table(name = "korpa")
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdKorpe", query = "SELECT k FROM Korpa k WHERE k.idKorpe = :idKorpe"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorpe")
    private Integer idKorpe;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika")
    @OneToOne(optional = false)
    private Korisnik idKorisnika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpa")
    private List<KorpaSadrzi> korpaSadrziList;

    public Korpa() {
    }

    public Korpa(Integer idKorpe) {
        this.idKorpe = idKorpe;
    }

    public Korpa(Integer idKorpe, BigDecimal ukupnaCena) {
        this.idKorpe = idKorpe;
        this.ukupnaCena = ukupnaCena;
    }

    public Integer getIdKorpe() {
        return idKorpe;
    }

    public void setIdKorpe(Integer idKorpe) {
        this.idKorpe = idKorpe;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public List<KorpaSadrzi> getKorpaSadrziList() {
        return korpaSadrziList;
    }

    public void setKorpaSadrziList(List<KorpaSadrzi> korpaSadrziList) {
        this.korpaSadrziList = korpaSadrziList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorpe != null ? idKorpe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idKorpe == null && other.idKorpe != null) || (this.idKorpe != null && !this.idKorpe.equals(other.idKorpe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korpa[ idKorpe=" + idKorpe + " ]";
    }
    
}
