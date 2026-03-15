package entiteti;

import entiteti.Korisnik;
import entiteti.NaListi;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(ListaZelja.class)
public class ListaZelja_ { 

    public static volatile SingularAttribute<ListaZelja, Date> datumKreiranja;
    public static volatile SingularAttribute<ListaZelja, Korisnik> idKorisnika;
    public static volatile ListAttribute<ListaZelja, NaListi> naListiList;
    public static volatile SingularAttribute<ListaZelja, Integer> idListe;

}