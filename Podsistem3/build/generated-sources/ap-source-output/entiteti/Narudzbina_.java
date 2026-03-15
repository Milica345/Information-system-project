package entiteti;

import entiteti.Korisnik;
import entiteti.Stavka;
import entiteti.Transakcija;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:41")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, BigDecimal> ukupnaCena;
    public static volatile SingularAttribute<Narudzbina, Date> vremeKreiranja;
    public static volatile SingularAttribute<Narudzbina, Transakcija> transakcija;
    public static volatile SingularAttribute<Narudzbina, String> gradZaDostavu;
    public static volatile SingularAttribute<Narudzbina, String> adresa;
    public static volatile ListAttribute<Narudzbina, Stavka> stavkaList;
    public static volatile SingularAttribute<Narudzbina, Korisnik> idKorisnika;
    public static volatile SingularAttribute<Narudzbina, Integer> idNarudzbine;

}