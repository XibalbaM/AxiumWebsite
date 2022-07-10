package fr.xibalba.axiumwebsite.api.repositories;

import fr.xibalba.axiumwebsite.api.tables.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.username = ?1 AND a.password = ?2")
    Account findByUsernameAndPassword(String username, String password);

    /**
     * Find an account by its token.
     *
     * @Param token The token to search.
     */
    @Query("SELECT a FROM Account a WHERE a.token = ?1")
    Account findByToken(String token);

    /**
     * Generate a new token for an account.
     *
     * @Param account The account to generate a new token for.
     */
    @Modifying
    @Query("UPDATE Account a SET a.token = ?2 WHERE a.id = ?1")
    void generateToken(Integer account, String token);

    /**
     * Discard an account's token.
     *
     * @Param account The account to discard its token.
     */
    @Modifying
    @Query("UPDATE Account a SET a.token = NULL, a.tokenExpiration = NULL WHERE a.id = ?1")
    void discardToken(Integer account);

    /**
     * Return the account's token.
     *
     * @param account
     * @Return If the account already have a token.
     */
    @Query("SELECT a.token FROM Account a WHERE a.id = ?1")
    String findToken(Integer account);

    /**
     * Find an account by its username.
     *
     * @Param username The username to search.
     * @Return The account id.
     */
    @Query("SELECT a.id FROM Account a WHERE a.username = ?1")
    Integer findByUsername(String username);

    /**
     * Find an account by its email.
     *
     * @Param email The email to search.
     * @Return The account id.
     */
    @Query("SELECT a.id FROM Account a WHERE a.email = ?1")
    Integer findByEmail(String email);
}
