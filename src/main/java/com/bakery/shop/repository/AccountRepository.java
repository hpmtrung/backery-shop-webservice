package com.bakery.shop.repository;

import com.bakery.shop.domain.Account;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Account} entity.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<Account> findOneByActivationKey(String activationKey);

    List<Account> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndResetDate(Instant dateTime);

    Optional<Account> findOneByResetKey(String resetKey);

    Optional<Account> findOneByEmailIgnoreCase(String email);

    Optional<Account> findOneByPhone(String phone);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<Account> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<Account> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
