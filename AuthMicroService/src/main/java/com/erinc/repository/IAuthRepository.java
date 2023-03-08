package com.erinc.repository;

import com.erinc.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    /**
     * Bu kullanıcı adı daha önce kullanılmış mı?
     */
    @Query(value = "select COUNT(a)>0 from Auth a where a.username=?1")
    boolean isUsername(String username);

    /**
     * Kullanıcı adı ve sifresi verilen kaydın olup olmadığı kontrol ediliyor.
     */
    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);
}
