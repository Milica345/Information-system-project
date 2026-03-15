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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author student
 */
@Entity
@Table(name = "uloga")
@NamedQueries({
    @NamedQuery(name = "Uloga.findAll", query = "SELECT u FROM Uloga u"),
    @NamedQuery(name = "Uloga.findByIdUloge", query = "SELECT u FROM Uloga u WHERE u.idUloge = :idUloge"),
    @NamedQuery(name = "Uloga.findByNaziv", query = "SELECT u FROM Uloga u WHERE u.naziv = :naziv")})
public class Uloga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUloge")
    private Integer idUloge;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUloge")
    private List<ImaUlogu> imaUloguList;

    public Uloga() {
    }

    public Uloga(Integer idUloge) {
        this.idUloge = idUloge;
    }

    public Uloga(Integer idUloge, String naziv) {
        this.idUloge = idUloge;
        this.naziv = naziv;
    }

    public Integer getIdUloge() {
        return idUloge;
    }

    public void setIdUloge(Integer idUloge) {
        this.idUloge = idUloge;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public List<ImaUlogu> getImaUloguList() {
        return imaUloguList;
    }

    public void setImaUloguList(List<ImaUlogu> imaUloguList) {
        this.imaUloguList = imaUloguList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUloge != null ? idUloge.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uloga)) {
            return false;
        }
        Uloga other = (Uloga) object;
        if ((this.idUloge == null && other.idUloge != null) || (this.idUloge != null && !this.idUloge.equals(other.idUloge))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Uloga[ idUloge=" + idUloge + " ]";
    }
    
}
