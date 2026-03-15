/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author student
 */
@Entity
@Table(name = "narudzbina")
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdNarudzbine", query = "SELECT n FROM Narudzbina n WHERE n.idNarudzbine = :idNarudzbine"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVremeKreiranja", query = "SELECT n FROM Narudzbina n WHERE n.vremeKreiranja = :vremeKreiranja"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Narudzbina.findByGradZaDostavu", query = "SELECT n FROM Narudzbina n WHERE n.gradZaDostavu = :gradZaDostavu")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idNarudzbine")
    private Integer idNarudzbine;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnaCena")
    private BigDecimal ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vremeKreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "gradZaDostavu")
    private String gradZaDostavu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNarudzbine")
    private List<Stavka> stavkaList;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idNarudzbine")
    private Transakcija transakcija;

    public Narudzbina() {
    }

    public Narudzbina(Integer idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public Narudzbina(Integer idNarudzbine, BigDecimal ukupnaCena, Date vremeKreiranja, String adresa, String gradZaDostavu) {
        this.idNarudzbine = idNarudzbine;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
        this.adresa = adresa;
        this.gradZaDostavu = gradZaDostavu;
    }

    public Integer getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(Integer idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(BigDecimal ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGradZaDostavu() {
        return gradZaDostavu;
    }

    public void setGradZaDostavu(String gradZaDostavu) {
        this.gradZaDostavu = gradZaDostavu;
    }

    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNarudzbine != null ? idNarudzbine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idNarudzbine == null && other.idNarudzbine != null) || (this.idNarudzbine != null && !this.idNarudzbine.equals(other.idNarudzbine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Narudzbina[ idNarudzbine=" + idNarudzbine + " ]";
    }
    
}
