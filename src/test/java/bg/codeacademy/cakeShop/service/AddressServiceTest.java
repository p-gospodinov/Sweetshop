package bg.codeacademy.cakeShop.service;

import bg.codeacademy.cakeShop.model.Address;
import bg.codeacademy.cakeShop.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    static AddressService addressService;
    static AddressRepository addressRepository = mock(AddressRepository.class);

    @BeforeAll
    public static void setup() {
        addressService = new AddressService(addressRepository);
    }

    @Test
    void shouldSaveAddress() {
        Address address = new Address();
        address.setCity("Sofia");
        address.setStreet("Arda");
        when(addressRepository.existsAddressByCityAndStreet(address.getCity()
                , address.getStreet())).thenReturn(false);
        Address response = addressService.addAddress(address);
        Assertions.assertEquals(address, response);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void shouldReturnSavedAddress() {
        Address address = new Address();
        address.setCity("Sofia");
        address.setStreet("Arda");
        when(addressRepository.existsAddressByCityAndStreet(address.getCity()
                , address.getStreet())).thenReturn(true);
        when(addressRepository.findAddressByCityAndStreet(address.getCity()
                , address.getStreet())).thenReturn(address);
        Address response = addressService.addAddress(address);
        Assertions.assertEquals(response, address);
    }
}