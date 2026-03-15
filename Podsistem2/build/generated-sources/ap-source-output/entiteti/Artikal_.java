package entiteti;

import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.KorpaSadrzi;
import entiteti.NaListi;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, BigDecimal> popustUProcentima;
    public static volatile ListAttribute<Artikal, KorpaSadrzi> korpaSadrziList;
    public static volatile SingularAttribute<Artikal, Integer> idArtikla;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile ListAttribute<Artikal, NaListi> naListiList;
    public static volatile SingularAttribute<Artikal, BigDecimal> cena;
    public static volatile SingularAttribute<Artikal, Kategorija> idKategorije;
    public static volatile SingularAttribute<Artikal, Korisnik> idProizvodjaca;
    public static volatile SingularAttribute<Artikal, String> opis;

}