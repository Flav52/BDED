package entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Embeddable
@NamedQueries({@NamedQuery(name=Compte.QN.ALL_COMPTE, query="select o FROM COMPTE o"),
               @NamedQuery(name=Compte.QN.FIND_COMPTE, query="select o FROM COMPTE o WHERE o.id = :IDENTIFIANT")
})
public class Compte implements Serializable {

    public static interface QN {
        String ALL_COMPTE = "Bank.allCompte";
        String FIND_COMPTE = "Bank.findCompte";
    }

    private static final long serialVersionUID = 0L;

    private int id;
    private long solde;

    public Compte() {
        setId(-1);
    }

    public Compte(final int numCompte) {
        setId(numCompte);
        setSolde(0);
    }

    public Compte(final int numCompte, long solde) {
        setId(numCompte);
        setSolde(solde);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSolde() {
        return solde;
    }

    public void setSolde(long solde) {
        this.solde = solde;
    }
 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append("[id=");
        sb.append(getId());
        sb.append(", solde=");
        sb.append(getSolde());
        sb.append("]");
        return sb.toString();
    }

}
