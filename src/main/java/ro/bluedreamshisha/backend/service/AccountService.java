package ro.bluedreamshisha.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bluedreamshisha.backend.model.auth.Account;
import ro.bluedreamshisha.backend.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account insert(Account account) {
        return accountRepository.save(account);
    }
}
