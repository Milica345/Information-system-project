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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author student
 */
@Entity
@Table(name = "artikal")
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIdArtikla", query = "SELECT a FROM Artikal a WHERE a.idArtikla = :idArtikla"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopustUProcentima", query = "SELECT a FROM Artikal a WHERE a.popustUProcentima = :popustUProcentima")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idArtikla")
    private Integer idArtikla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "opis")
    private String opis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private BigDecimal cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "popustUProcentima")
    private BigDecimal popustUProcentima;
    @JoinColumn(name = "idKategorije", referencedColumnName = "idKategorije")
    @ManyToOne
    private Kategorija idKategorije;
    @JoinColumn(name = "idProizvodjaca", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    private Korisnik idProizvodjaca;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    private List<KorpaSadrzi> korpaSadrziList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikal")
    private List<NaListi> naListiList;

    public Artikal() {
    }

    public Artikal(Integer idArtikla) {
        this.idArtikla = idArtikla;
    }

    public Artikal(Integer idArtikla, String naziv, String opis, BigDecimal cena, BigDecimal popustUProcentima) {
        this.idArtikla = idArtikla;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.popustUProcentima = popustUProcentima;
    }

    public Integer getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(Integer idArtikla) {
        this.idArtikla = idArtikla;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public BigDecimal getPopustUProcentima() {
        return popustUProcentima;
    }

    public void setPopustUProcentima(BigDecimal popustUProcentima) {
        this.popustUProcentima = popustUProcentima;
    }

    public Kategorija getIdKategorije() {
        return idKategorije;
    }

    public void setIdKategorije(Kategorija idKategorije) {
        this.idKategorije = idKategorije;
    }

    public Korisnik getIdProizvodjaca() {
        return idProizvodjaca;
    }

    public void setIdProizvodjaca(Korisnik idProizvodjaca) {
        this.idProizvodjaca = idProizvodjaca;
    }

    public List<KorpaSadrzi> getKorpaSadrziList() {
        return korpaSadrziList;
    }

    public void setKorpaSadrziList(List<KorpaSadrzi> korpaSadrziList) {
        this.korpaSadrziList = korpaSadrziList;
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
        hash += (idArtikla != null ? idArtikla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idArtikla == null && other.idArtikla != null) || (this.idArtikla != null && !this.idArtikla.equals(other.idArtikla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikal[ idArtikla=" + idArtikla + " ]";
    }
    
}
