package banking;


import entity.Compte;

import java.util.List;

public interface Bank {

    void addCompte(final int id);
    void addCompte(final Compte compte);
    void removeCompte(final Compte Compte);

    List<Compte> allCompte();
    Compte findCompte(int id);

}
