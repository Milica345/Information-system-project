/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
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

/**
 *
 * @author student
 */
@Entity
@Table(name = "imaUlogu")
@NamedQueries({
    @NamedQuery(name = "ImaUlogu.findAll", query = "SELECT i FROM ImaUlogu i"),
    @NamedQuery(name = "ImaUlogu.findByIdImaUlogu", query = "SELECT i FROM ImaUlogu i WHERE i.idImaUlogu = :idImaUlogu")})
public class ImaUlogu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idImaUlogu")
    private Integer idImaUlogu;
    @JoinColumn(name = "idKorisnika", referencedColumnName = "idKorisnika")
    @ManyToOne(optional = false)
    private Korisnik idKorisnika;
    @JoinColumn(name = "idUloge", referencedColumnName = "idUloge")
    @ManyToOne(optional = false)
    private Uloga idUloge;

    public ImaUlogu() {
    }

    public ImaUlogu(Integer idImaUlogu) {
        this.idImaUlogu = idImaUlogu;
    }

    public Integer getIdImaUlogu() {
        return idImaUlogu;
    }

    public void setIdImaUlogu(Integer idImaUlogu) {
        this.idImaUlogu = idImaUlogu;
    }

    public Korisnik getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(Korisnik idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Uloga getIdUloge() {
        return idUloge;
    }

    public void setIdUloge(Uloga idUloge) {
        this.idUloge = idUloge;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImaUlogu != null ? idImaUlogu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImaUlogu)) {
            return false;
        }
        ImaUlogu other = (ImaUlogu) object;
        if ((this.idImaUlogu == null && other.idImaUlogu != null) || (this.idImaUlogu != null && !this.idImaUlogu.equals(other.idImaUlogu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ImaUlogu[ idImaUlogu=" + idImaUlogu + " ]";
    }
    
}
