package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.enums.BankAccountType;
import bg.codeacademy.cakeShop.enums.Role;
import bg.codeacademy.cakeShop.enums.Status;
import bg.codeacademy.cakeShop.error_handling.exception.ItemStockException;
import bg.codeacademy.cakeShop.model.BankAccount;
import bg.codeacademy.cakeShop.model.Contract;
import bg.codeacademy.cakeShop.model.Storage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class PurchaseService {
    private static final int RENTIER_PERCENTAGE = 30;
    private final StorageService storageService;
    private final TurnoverService turnoverService;
    private final BankAccountService bankAccountService;
    private final ContractService contractService;
    private final ExchangeRateService exchangeRateService;
    @Value("${purchase.profit-percentage}")
    private int profitPercentage;

    public PurchaseService(StorageService storageService,
                           TurnoverService turnoverService,
                           BankAccountService bankAccountService, ContractService contractService, ExchangeRateService exchangeRateService) {
        this.storageService = storageService;
        this.turnoverService = turnoverService;
        this.bankAccountService = bankAccountService;
        this.contractService = contractService;
        this.exchangeRateService = exchangeRateService;
    }

    @Transactional
    public Map<String, Integer> purchaseItems(int shopId, Map<String, Integer> purchaseList) {
        float totalSum = 0;
        for (Map.Entry<String, Integer> entry : purchaseList.entrySet()) {
            String itemName = entry.getKey();
            int itemCount = entry.getValue();
            Storage storageRow = storageService.getStorage(itemName, shopId);
            if (storageRow.getCount() <= 0 || storageRow.getCount() < itemCount) {
                log.error("Service | The stock of item:" + entry.getKey()
                        + " is not enough to purchase it!");
                throw new ItemStockException("The stock of item:" + entry.getKey()
                        + " is not enough to purchase it!");
            }
            int finalCount = storageRow.getCount() - itemCount;
            storageRow.setCount(finalCount);
            storageService.updateStorage(storageRow);
            float profit = calculatePercentage(storageRow.getItem().getPrice(), profitPercentage);
            float totalProfit = profit * itemCount;
            totalSum += (storageRow.getItem().getPrice() * itemCount) + totalProfit;
        }
        turnoverService.additionAmount(shopId, totalSum);
        BankAccount rentalAccount = bankAccountService.getBankAccount(shopId, BankAccountType.RENTAL);
        Contract contractWithRentier = contractService.getContract(shopId, Status.SIGNED, Role.RENTIER);
        if (rentalAccount.getAmount() < contractWithRentier.getAmount()) {
            //Get 30% of the total amount here
            float forRent = calculatePercentage(totalSum, RENTIER_PERCENTAGE);
            float toEuro = (float) exchangeRateService.convert(forRent);
            totalSum -= forRent;
            rentalAccount.setAmount(rentalAccount.getAmount() + toEuro);
            bankAccountService.update(rentalAccount);
        } else {
            System.out.println("The money for rent are collected.");
        }
        BankAccount generalAccount = bankAccountService.getBankAccount(shopId, BankAccountType.GENERAL);
        generalAccount.setAmount(totalSum);
        bankAccountService.update(generalAccount);
        return purchaseList;
    }

    private float calculatePercentage(float inValue, int percent) {
        float percentFloat = (float) percent / 100;
        return (inValue * percentFloat);
    }
}
