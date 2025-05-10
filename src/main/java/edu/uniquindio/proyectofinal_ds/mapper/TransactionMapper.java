package edu.uniquindio.proyectofinal_ds.mapper;

import edu.uniquindio.proyectofinal_ds.dto.TransferDTO;
import edu.uniquindio.proyectofinal_ds.dto.WithdrawDTO;
import edu.uniquindio.proyectofinal_ds.model.Deposit;
import edu.uniquindio.proyectofinal_ds.dto.DepositDTO;
import edu.uniquindio.proyectofinal_ds.model.Transfer;
import edu.uniquindio.proyectofinal_ds.model.Withdraw;

public class TransactionMapper {

    public static Deposit toDeposit(DepositDTO dto) {
        return new Deposit(dto.getAccountId(), dto.getAmount());
    }

    public static DepositDTO toDepositDTO(Deposit deposit) {
        return new DepositDTO(deposit.getAccountId(), deposit.getAmount());
    }

    public static Withdraw toWithdraw(WithdrawDTO dto) {
        return new Withdraw(dto.getAccountId(), dto.getAmount());
    }

    public static WithdrawDTO toWithdrawDTO(Withdraw withdraw) {
        return new WithdrawDTO(withdraw.getAccountId(), withdraw.getAmount());
    }

    public static Transfer toTransfer(TransferDTO dto) {
        return new Transfer(dto.getSourceAccountId(), dto.getDestinationAccountId(), dto.getAmount());
    }

    public static TransferDTO toTransferDTO(Transfer transfer) {
        return new TransferDTO(transfer.getAccountId(), transfer.getDestinationAccountId(), transfer.getAmount());
    }
}