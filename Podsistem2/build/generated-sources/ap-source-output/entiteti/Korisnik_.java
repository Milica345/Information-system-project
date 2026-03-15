package entiteti;

import entiteti.Artikal;
import entiteti.Korpa;
import entiteti.ListaZelja;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, ListaZelja> listaZelja;
    public static volatile SingularAttribute<Korisnik, Korpa> korpa;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnika;
    public static volatile SingularAttribute<Korisnik, String> korisnickoIme;
    public static volatile ListAttribute<Korisnik, Artikal> artikalList;

}