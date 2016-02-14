package art.alex.services;

import art.alex.entities.User;

import java.math.BigDecimal;

/**
 * Determines current credit calculations for a User
 */
public interface CreditDataService {

    /**
     * Determines credit limit for user
     *
     * @param user user to determine credit limit for
     * @return credit limit
     */
    BigDecimal determineCreditLimit(User user);


    /**
     * Sets credit limit for user
     * @param user user to set credit limit for
     * @return user with credit limit set
     */
    User setCreditLimit(User user);

}