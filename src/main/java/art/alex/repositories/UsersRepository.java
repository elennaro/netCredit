package art.alex.repositories;

import art.alex.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>, UsersRepositoryCustom {

    User findById(Long id);

    User findByUsername(String lastName);

}
