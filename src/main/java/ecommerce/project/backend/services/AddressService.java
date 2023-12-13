package ecommerce.project.backend.services;

import ecommerce.project.backend.dto.AddressDTO;
import ecommerce.project.backend.entities.Address;

import java.util.List;

public interface AddressService {
    Address saveAddress(Address address);
    Address findAddressById(Long addressId);
    AddressDTO createAddress(AddressDTO addressDTO);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    List<AddressDTO> findUserAddresses(Long userId);
    void deleteAddress(Long addressId);
}
