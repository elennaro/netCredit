package art.alex.services;

import art.alex.entities.User;

import java.math.BigDecimal;

/*
* used to determine credit limit
*/
public interface CreditDataService {

    /**
     * Determines credit limit for user
     */
    BigDecimal determineCreditLimit(User user);


    /**
     * Sets credit limit for user
     */
    User setCreditLimit(User user);

}