package entiteti;

import entiteti.Korisnik;
import entiteti.Narudzbina;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:41")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, BigDecimal> jedinicnaCena;
    public static volatile SingularAttribute<Stavka, Integer> idStavke;
    public static volatile SingularAttribute<Stavka, Integer> idArtikla;
    public static volatile SingularAttribute<Stavka, Korisnik> idProdavca;
    public static volatile SingularAttribute<Stavka, Integer> kolicinaArtikla;
    public static volatile SingularAttribute<Stavka, Narudzbina> idNarudzbine;

}