package entiteti;

import entiteti.Korisnik;
import entiteti.KorpaSadrzi;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(Korpa.class)
public class Korpa_ { 

    public static volatile SingularAttribute<Korpa, BigDecimal> ukupnaCena;
    public static volatile ListAttribute<Korpa, KorpaSadrzi> korpaSadrziList;
    public static volatile SingularAttribute<Korpa, Korisnik> idKorisnika;
    public static volatile SingularAttribute<Korpa, Integer> idKorpe;

}