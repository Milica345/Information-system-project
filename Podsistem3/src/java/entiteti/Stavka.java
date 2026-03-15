/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "stavka")
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdStavke", query = "SELECT s FROM Stavka s WHERE s.idStavke = :idStavke"),
    @NamedQuery(name = "Stavka.findByKolicinaArtikla", query = "SELECT s FROM Stavka s WHERE s.kolicinaArtikla = :kolicinaArtikla"),
    @NamedQuery(name = "Stavka.findByIdArtikla", query = "SELECT s FROM Stavka s WHERE s.idArtikla = :idArtikla"),
    @NamedQuery(name = "Stavka.findByJedinicnaCena", query = "SELECT s FROM Stavka s WHERE s.jedinicnaCena = :jedinicnaCena")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStavke")
    private Integer idStavke;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicinaArtikla")
    private int kolicinaArtikla;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idArtikla")
    private int idArtikla;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "jedinicnaCena")
    private BigDecimal jedinicnaCena;
    @JoinColumn(name = "idProdavca", referencedColumnName = "idKorisnika")
    @ManyToOne
    private Korisnik idProdavca;
    @JoinColumn(name = "idNarudzbine", referencedColumnName = "idNarudzbine")
    @ManyToOne(optional = false)
    private Narudzbina idNarudzbine;

    public Stavka() {
    }

    public Stavka(Integer idStavke) {
        this.idStavke = idStavke;
    }

    public Stavka(Integer idStavke, int kolicinaArtikla, int idArtikla, BigDecimal jedinicnaCena) {
        this.idStavke = idStavke;
        this.kolicinaArtikla = kolicinaArtikla;
        this.idArtikla = idArtikla;
        this.jedinicnaCena = jedinicnaCena;
    }

    public Integer getIdStavke() {
        return idStavke;
    }

    public void setIdStavke(Integer idStavke) {
        this.idStavke = idStavke;
    }

    public int getKolicinaArtikla() {
        return kolicinaArtikla;
    }

    public void setKolicinaArtikla(int kolicinaArtikla) {
        this.kolicinaArtikla = kolicinaArtikla;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
    }

    public BigDecimal getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(BigDecimal jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Korisnik getIdProdavca() {
        return idProdavca;
    }

    public void setIdProdavca(Korisnik idProdavca) {
        this.idProdavca = idProdavca;
    }

    public Narudzbina getIdNarudzbine() {
        return idNarudzbine;
    }

    public void setIdNarudzbine(Narudzbina idNarudzbine) {
        this.idNarudzbine = idNarudzbine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStavke != null ? idStavke.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.idStavke == null && other.idStavke != null) || (this.idStavke != null && !this.idStavke.equals(other.idStavke))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavka[ idStavke=" + idStavke + " ]";
    }
    
}
