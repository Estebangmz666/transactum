package edu.uniquindio.proyectofinal_ds.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import edu.uniquindio.proyectofinal_ds.dto.AccountDTO;
import edu.uniquindio.proyectofinal_ds.model.Account;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toAccount(AccountDTO dto);
    AccountDTO toDTO(Account account);
}