package bg.codeacademy.cakeShop.mapper;

import bg.codeacademy.cakeShop.dto.*;
import bg.codeacademy.cakeShop.enums.Currency;
import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Mapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Mapper(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public LegalEntity mapToLegalEntity(LegalEntityRegistrationDTO dto) {
        PersonalData personalData = mapToPersonalData(dto.personalData());
        LegalEntity legalEntity = new LegalEntity();
        legalEntity.setUin(dto.uin());
        legalEntity.setEmail(dto.email());
        legalEntity.setPersonalData(personalData);
        return legalEntity;
    }

    public PersonalData mapToPersonalData(PersonalDataDTO dto) {
        Address address = new Address();
        address.setCity(dto.address().city());
        address.setStreet(dto.address().street());
        PersonalData personalData = new PersonalData();
        personalData.setUserName(dto.userName());
        String encoded = bCryptPasswordEncoder.encode(dto.password());
        System.out.println(encoded);
        personalData.setUserPassword(encoded);
        personalData.setUserRole(Role.valueOf(dto.role()));
        personalData.setAddress(address);
        personalData.setPersonalName(dto.personalName());
        List<BankAccountDTO> dtoList = dto.bankAccount();
        List<BankAccount> accounts = new LinkedList<>();

        for (BankAccountDTO b : dtoList) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setIban(b.iban());
            bankAccount.setAmount(b.amount());
            bankAccount.setCurrency(Currency.valueOf(b.currency()));
            bankAccount.setBankAccountType(b.bankAccountType());
            bankAccount.setBeneficiary(personalData);
            accounts.add(bankAccount);
        }
        personalData.setBankAccount(accounts);
        return personalData;
    }

    public LegalEntityResponse mapLegalEntityToResponse(LegalEntity legalEntity) {
        List<BankAccount> bankAccountList = legalEntity.getPersonalData().getBankAccount();
        List<BankAccountDTO> bankAccountDTOList = new LinkedList<>();
        for (BankAccount bAcc : bankAccountList) {
            BankAccountDTO bAccDto = new BankAccountDTO(
                    bAcc.getIban(),
                    bAcc.getAmount(),
                    String.valueOf(bAcc.getCurrency()),
                    bAcc.getBankAccountType()
            );
            bankAccountDTOList.add(bAccDto);
        }
        Address address = legalEntity.getPersonalData().getAddress();
        AddressDTO addressDTO = new AddressDTO(address.getCity(), address.getStreet());
        PersonalData pd = legalEntity.getPersonalData();
        PersonalDataDTO personalData = new PersonalDataDTO(
                pd.getUserName(),
                "",
                String.valueOf(pd.getUserRole()),
                pd.getPersonalName(),
                addressDTO,
                bankAccountDTOList
        );
        return new LegalEntityResponse(
                legalEntity.getId(),
                legalEntity.getEmail(),
                legalEntity.getUin(),
                personalData);
    }

    public List<LegalEntityResponse> mapLegalEntityToResponseList(List<LegalEntity> legalEntities) {
        List<LegalEntityResponse> responseList = new ArrayList<>();
        for (LegalEntity le : legalEntities) {
            LegalEntityResponse response = mapLegalEntityToResponse(le);
            responseList.add(response);
        }
        return responseList;
    }

    public Map<String, List<OfferResponceDTO>> mapToOfferResponceDTOList(Map<String, List<Offer>> offers) {

        Map<String, List<OfferResponceDTO>> dtoResponse = new HashMap<>();

        for (Map.Entry<String, List<Offer>> entry : offers.entrySet()) {
            List<OfferResponceDTO> list = new ArrayList<>();
            for (Offer of : entry.getValue()) {
                OfferResponceDTO offerResponse = new OfferResponceDTO(
                        of.getMoney(),
                        of.getOfferor().getUin(),
                        of.getOfferor().getPersonalData().getPersonalName(),
                        of.getOfferor().getEmail(),
                        of.getContract().getIdentifier()
                );
                list.add(offerResponse);
            }
            dtoResponse.put(entry.getKey(), list);
        }
        return dtoResponse;
    }

    public List<ScheduleTransactionDTO> mapToScheduleTransactionDTOList(List<ScheduleTransaction> transactionList) {
        List<ScheduleTransactionDTO> scheduleTransactionDTOS = new ArrayList<>();
        for (ScheduleTransaction account : transactionList) {
            ScheduleTransactionDTO dto = new ScheduleTransactionDTO(
                    account.getSender().getIban(),
                    account.getRecipient().getIban(),
                    account.getAmountPercentage(),
                    String.valueOf(account.getPaymentCriteria()));
            scheduleTransactionDTOS.add(dto);
        }
        return scheduleTransactionDTOS;
    }

    public Map<String, List<ContractResponseDTO>> mapToContractListDto(Map<String, List<Contract>> contractList) {
        Map<String, List<ContractResponseDTO>> dtoResponse = new HashMap<>();
        for (Map.Entry<String, List<Contract>> entry : contractList.entrySet()) {
            List<ContractResponseDTO> list = new ArrayList<>();
            for (Contract of : entry.getValue()) {
                ContractResponseDTO contractDto = new ContractResponseDTO(
                        of.getIdentifier(),
                        of.getAmount(),
                        String.valueOf(of.getCurrency()),
                        of.getOfferor().getUin(),
                        of.getRecipient().getUin(),
                        String.valueOf(of.getStatus())
                );
                list.add(contractDto);
            }
            dtoResponse.put(entry.getKey(), list);
        }
        return dtoResponse;
    }

    public List<CommentResponseDTO> mapToCommentListDto(List<Comment> commentList) {
        List<CommentResponseDTO> comments = new ArrayList<>();
        for (Comment c : commentList) {
            CommentResponseDTO dto = new CommentResponseDTO(
                    c.getComment(),
                    c.getAssessed().getUin(),
                    c.getDate());
            comments.add(dto);
        }
        return comments;
    }

    public List<BankAccountDTO> mapToBankAccountDtoList(List<BankAccount> bankAccountList) {
        List<BankAccountDTO> dtoList = new ArrayList<>();
        for (BankAccount account : bankAccountList) {
            BankAccountDTO dto = new BankAccountDTO(
                    account.getIban(),
                    account.getAmount(),
                    String.valueOf(account.getCurrency()),
                    account.getBankAccountType());
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Map<Item, Integer> mapDeliveryRequestDTOToItemsList(List<ItemDTO> items) {
        Map<Item, Integer> itemsList = new HashMap<>();
        for (ItemDTO dto : items) {
            Item item = new Item();
            item.setName(dto.name());
            item.setPrice(dto.price());
            itemsList.put(item, dto.count());
        }
        return itemsList;
    }

    public Map<String, Integer> mapTransferItemDto(List<TransferItemDTO> items) {
        Map<String, Integer> itemsList = new HashMap<>();
        for (TransferItemDTO dto : items) {
            itemsList.put(dto.name(), dto.count());
        }
        return itemsList;
    }

    public Map<String, Integer> mapToItemList(List<Storage> storage) {
        Map<String, Integer> items = new HashMap<>();
        for (Storage row : storage) {
            items.put(row.getItem().getName(), row.getCount());
        }
        return items;
    }
}
