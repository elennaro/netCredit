package art.alex.repositories;


import art.alex.entities.User;

public interface UsersRepositoryCustom {

    /**
     * Registers new user
     *
     * @param user user to register
     * @return registered user
     */
    User registerUser(User user);

    /**
     * Saves user with new encrypted password
     *
     * @param user user to save (Must contain unencrypted password)
     * @return registered user
     */
    User updatePassword(User user);

}
