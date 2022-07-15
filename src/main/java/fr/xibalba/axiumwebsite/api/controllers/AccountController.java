package fr.xibalba.axiumwebsite.api.controllers;

import fr.xibalba.axiumwebsite.api.repositories.AccountRepository;
import fr.xibalba.axiumwebsite.api.tables.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    Supplier<String> tokenSupplier = () -> {

        StringBuilder token = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();
        return token.append(currentTimeInMilisecond).append("-")
                .append(UUID.randomUUID().toString()).toString();
    };

    @RequestMapping("/connect")
    public Account connect(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        Account account = accountRepository.findByUsernameAndPassword(username, password);

        if (account == null) {
            return null;
        }

        if (accountRepository.findToken(account.getId()) == null) {

            accountRepository.generateToken(account.getId(), tokenSupplier.get());
        }

        account.setPassword(null);

        return account;
    }

    @RequestMapping("/disconnect")
    public void disconnect(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        accountRepository.discardToken(accountRepository.findByUsernameAndPassword(username, password).getId());
    }

    @RequestMapping("/tokenDisconnect")
    public void tokenDisconnect(@RequestParam(value = "token") String token) {

        accountRepository.discardToken(((Account) accountRepository.findByToken(token)).getId());
    }

    @RequestMapping("")
    public Account infos(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "id", required = false) Integer id) {

        if (username != null && accountRepository.findByUsername(username) != null) {

            id = accountRepository.findByUsername(username);
        }

        if (id != null && accountRepository.findById(id).isPresent()) {

            Account account = accountRepository.findById(id).get().setPassword(null).setToken(null);

            return account;
        } else {

            return null;
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException e) {

        e.printStackTrace();
    }
}
