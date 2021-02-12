package init;

import entity.Compte;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(mappedName="myInitializerBean")
@Remote(Initializer.class)
public class InitializerBean implements Initializer {

    @EJB
    private banking.LocalBank bank;

    public void initializeEntities() {
        for(int i=0;i<10;i++)
            if (bank.findCompte(i) == null) {
                bank.addCompte(i);
            }
    }

}
