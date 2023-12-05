package br.com.gamescan.appuser;


import br.com.gamescan.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    Long countById(Integer id);

    Optional<AppUser> findByEmail(String email);
}