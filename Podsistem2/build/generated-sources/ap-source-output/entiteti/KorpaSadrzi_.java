package entiteti;

import entiteti.Artikal;
import entiteti.Korpa;
import entiteti.KorpaSadrziPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(KorpaSadrzi.class)
public class KorpaSadrzi_ { 

    public static volatile SingularAttribute<KorpaSadrzi, KorpaSadrziPK> korpaSadrziPK;
    public static volatile SingularAttribute<KorpaSadrzi, Korpa> korpa;
    public static volatile SingularAttribute<KorpaSadrzi, Artikal> artikal;
    public static volatile SingularAttribute<KorpaSadrzi, Integer> kolicina;

}