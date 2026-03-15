/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author student
 */
@Entity
@Table(name = "korisnik")
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIdKorisnika", query = "SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKorisnika"),
    @NamedQuery(name = "Korisnik.findByKorisnickoIme", query = "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :korisnickoIme")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKorisnika")
    private Integer idKorisnika;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "korisnickoIme")
    private String korisnickoIme;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private ListaZelja listaZelja;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idKorisnika")
    private Korpa korpa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProizvodjaca")
    private List<Artikal> artikalList;

    public Korisnik() {
    }

    public Korisnik(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Korisnik(Integer idKorisnika, String korisnickoIme) {
        this.idKorisnika = idKorisnika;
        this.korisnickoIme = korisnickoIme;
    }

    public Integer getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Integer idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public ListaZelja getListaZelja() {
        return listaZelja;
    }

    public void setListaZelja(ListaZelja listaZelja) {
        this.listaZelja = listaZelja;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    public List<Artikal> getArtikalList() {
        return artikalList;
    }

    public void setArtikalList(List<Artikal> artikalList) {
        this.artikalList = artikalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKorisnika != null ? idKorisnika.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.idKorisnika == null && other.idKorisnika != null) || (this.idKorisnika != null && !this.idKorisnika.equals(other.idKorisnika))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ idKorisnika=" + idKorisnika + " ]";
    }
    
}
