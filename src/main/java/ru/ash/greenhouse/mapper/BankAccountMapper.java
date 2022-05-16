package ru.ash.greenhouse.mapper;

import org.springframework.web.bind.annotation.Mapping;
import ru.ash.greenhouse.model.BankAccountDto;
import ru.ash.greenhouse.model.BankAccountEntity;

import java.math.RoundingMode;
import java.util.List;

@Mapper(imports = {RoundingMode.class})
public interface BankAccountMapper {

    @Mapping(target = "numberAccount", expression = "java(BankAccountEntity.generationNumberAccount())")
    @Mapping(target = "balance",
            expression = "java(bankAccountDto.getBalance().setScale(bankAccountDto.getCurrency().getDefaultFractionDigits()," +
                    " RoundingMode.HALF_UP))")
    BankAccountEntity toEntity(BankAccountDto bankAccountDto);

    BankAccountDto toDto(BankAccountEntity bankAccountEntity);

    List<BankAccountDto> toDtos(List<BankAccountEntity> bankAccountDtos);

}