package backend_socket.backend_socket.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByStatus(UserStatus userStatus);

    List<User> findAllByUsernameContainingIgnoreCase(String username);

    List<User> findAllByUsernameIn(List<String> usernames);
}
