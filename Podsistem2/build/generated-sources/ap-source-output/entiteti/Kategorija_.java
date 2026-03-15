package entiteti;

import entiteti.Artikal;
import entiteti.Kategorija;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile ListAttribute<Kategorija, Kategorija> kategorijaList;
    public static volatile SingularAttribute<Kategorija, Kategorija> idNadkategorije;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile SingularAttribute<Kategorija, Integer> idKategorije;
    public static volatile ListAttribute<Kategorija, Artikal> artikalList;

}