package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.error_handling.exception.RoleNotSupportedException;
import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.model.LegalEntity;
import bg.codeacademy.cakeShop.model.PersonalData;
import bg.codeacademy.cakeShop.model.Staff;
import bg.codeacademy.cakeShop.repository.StaffRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final AddressService addressService;
    private final BankAccountService bankAccountService;
    private final PersonalDataService personalDataService;
    private final LegalEntityService legalEntityService;
    public final StaffRepository staffRepository;
    @Value("#{'${roles.staff}'.split(',')}")
    private List<String> roles;

    public StaffService(AddressService addressService, BankAccountService bankAccountService, PersonalDataService personalDataService, LegalEntityService legalEntityService, StaffRepository staffRepository) {
        this.addressService = addressService;
        this.bankAccountService = bankAccountService;
        this.personalDataService = personalDataService;
        this.legalEntityService = legalEntityService;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Staff createStaff(PersonalData personalData, int principalId) {
        Address address = addressService.addAddress(personalData.getAddress());
        personalData.setAddress(address);
        personalDataService.addPersonalData(personalData);
        bankAccountService.createBankAccount(personalData.getBankAccount());

        LegalEntity legalEntity = legalEntityService.getLegalEntity(principalId);

        Staff staff = new Staff();
        staff.setEmployer(legalEntity);
        staff.setPersonalData(personalData);

        String role = String.valueOf(staff.getPersonalData().getUserRole());
        if (roles.contains(role)) {
            staffRepository.save(staff);
            return staff;
        } else {
            throw new RoleNotSupportedException("Allowed roles for staff are:" + roles);
        }
    }
}
