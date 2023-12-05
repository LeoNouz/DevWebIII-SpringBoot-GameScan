package br.com.gamescan.appadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppAdminRepository extends JpaRepository<AppAdmin, Integer> {
    Long countById(Integer id);

    Optional<AppAdmin> findByEmail(String email);
}