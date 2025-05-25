package edu.uniquindio.proyectofinal_ds.mapper;

import edu.uniquindio.proyectofinal_ds.dto.AccountDTO;
import edu.uniquindio.proyectofinal_ds.model.Account;
import edu.uniquindio.proyectofinal_ds.model.AccountType;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-25T01:26:18-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(AccountDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AccountType accountType = null;
        UUID userId = null;
        String accountNumber = null;

        accountType = dto.getAccountType();
        userId = dto.getUserId();
        accountNumber = dto.getAccountNumber();

        Account account = new Account( userId, accountType, accountNumber );

        account.setBalance( dto.getBalance() );

        return account;
    }

    @Override
    public AccountDTO toDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountNumber( account.getAccountNumber() );
        accountDTO.setAccountType( account.getAccountType() );
        accountDTO.setBalance( account.getBalance() );
        accountDTO.setUserId( account.getUserId() );

        return accountDTO;
    }
}
