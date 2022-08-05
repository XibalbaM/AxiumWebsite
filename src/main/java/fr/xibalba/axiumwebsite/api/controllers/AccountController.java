package fr.xibalba.axiumwebsite.api.controllers;

import fr.xibalba.axiumwebsite.api.RestResponse;
import fr.xibalba.axiumwebsite.api.repositories.AccountRepository;
import fr.xibalba.axiumwebsite.api.tables.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static fr.xibalba.axiumwebsite.api.ApiError.Account.*;
import static fr.xibalba.axiumwebsite.api.ApiError.Global.*;
import static fr.xibalba.axiumwebsite.api.RestResponse.*;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    @Autowired
    public AccountRepository accountRepository;

    public static final Supplier<String> tokenSupplier = () -> {

        StringBuilder token = new StringBuilder();
        long currentTimeInMilisecond = Instant.now().toEpochMilli();
        return token.append(currentTimeInMilisecond).append("-")
                .append(UUID.randomUUID().toString()).toString();
    };

    @RequestMapping("/connect")
    public RestResponse<Account> connect(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        Account account = accountRepository.findByUsernameAndPassword(username, password);

        if (account == null) {

            if (accountRepository.findByUsername(username) == null) {

                return error(ACCOUNT_NOT_FOUND);
            } else {

                return error(BAD_CREDENTIALS);
            }
        }

        if (!account.isEnabled()) {

            return error(ACCOUNT_IS_BANNED_OR_DISABLED);
        }

        if (account.getToken() == null) {

            String token = tokenSupplier.get();
            accountRepository.generateToken(account.getId(), token);
            account.setToken(token);
        }

        account.setPassword(null);

        return success(account);
    }

    @RequestMapping("/disconnect")
    public RestResponse disconnect(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        Account account = accountRepository.findByUsernameAndPassword(username, password);

        if (account == null) {

            if (accountRepository.findByUsername(username) == null) {

                return error(ACCOUNT_NOT_FOUND);
            } else {

                return error(BAD_CREDENTIALS);
            }
        }

        if (account.getToken() == null) {

            return error(REQUEST_ALREADY_DONE_OR_IN_PROGRESS);
        }

        accountRepository.discardToken(accountRepository.findByUsernameAndPassword(username, password).getId());

        return success();
    }

    @RequestMapping("/tokenDisconnect")
    public RestResponse tokenDisconnect(@RequestParam(value = "token") String token) {

        Account account = accountRepository.findByToken(token);

        if (account == null) {

            return error(INVALID_TOKEN);
        }

        if (account.getToken() == null) {

            return error(REQUEST_ALREADY_DONE_OR_IN_PROGRESS);
        }

        accountRepository.discardToken(accountRepository.findByToken(token).getId());

        return success();
    }

    @RequestMapping("")
    public RestResponse<Account> infos(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "id", required = false) Integer id) {

        if (username != null) {

            Account account = accountRepository.findByUsername(username);

            if (account != null) {

                return success(account);
            } else {

                return error(ACCOUNT_NOT_FOUND);
            }
        } else if (id != null) {

            Optional<Account> optionalAccount = accountRepository.findById(id);

            return optionalAccount.map(account -> RestResponse.success(account.setPassword(null).setToken(null)))
                    .orElseGet(() -> error(ACCOUNT_NOT_FOUND));
        } else {

            return error(MISSING_PARAMETER);
        }
    }
}
