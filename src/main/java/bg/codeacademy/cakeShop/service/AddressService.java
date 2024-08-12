package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressService {
    public final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        if (!addressRepository.existsAddressByCityAndStreet(address.getCity(), address.getStreet())) {
            addressRepository.save(address);
            log.info("Service | Save address Street=" + address.getStreet() + ", City=" + address.getCity());
            return address;
        }
        log.info("Service | Find address Street=" + address.getStreet() + ", City=" + address.getCity());
        return addressRepository.findAddressByCityAndStreet(address.getCity(), address.getStreet());
    }
}
