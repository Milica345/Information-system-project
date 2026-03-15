package entiteti;

import entiteti.Artikal;
import entiteti.ListaZelja;
import entiteti.NaListiPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.4.v20190115-rNA", date="2026-03-13T18:50:39")
@StaticMetamodel(NaListi.class)
public class NaListi_ { 

    public static volatile SingularAttribute<NaListi, Date> vremeDodavanja;
    public static volatile SingularAttribute<NaListi, ListaZelja> listaZelja;
    public static volatile SingularAttribute<NaListi, NaListiPK> naListiPK;
    public static volatile SingularAttribute<NaListi, Artikal> artikal;

}