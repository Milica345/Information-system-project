package entiteti;

import entiteti.Narudzbina;
import entiteti.Stavka;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:41")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile ListAttribute<Korisnik, Narudzbina> narudzbinaList;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnika;
    public static volatile ListAttribute<Korisnik, Stavka> stavkaList;
    public static volatile SingularAttribute<Korisnik, String> korisnickoIme;

}