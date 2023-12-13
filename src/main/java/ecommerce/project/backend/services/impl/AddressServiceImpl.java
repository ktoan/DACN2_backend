package ecommerce.project.backend.services.impl;

import ecommerce.project.backend.dto.AddressDTO;
import ecommerce.project.backend.entities.Address;
import ecommerce.project.backend.entities.User;
import ecommerce.project.backend.exceptions.NotAccessException;
import ecommerce.project.backend.exceptions.NotFoundException;
import ecommerce.project.backend.mappers.AddressMapper;
import ecommerce.project.backend.repositories.AddressRepository;
import ecommerce.project.backend.services.AddressService;
import ecommerce.project.backend.services.UserService;
import ecommerce.project.backend.utils.context.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ecommerce.project.backend.constants.Messaging.ADDRESS_NOT_FOUND_ID_MSG;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final ContextService contextService;

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException(String.format(ADDRESS_NOT_FOUND_ID_MSG, addressId)));
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address address = addressMapper.toEntity(addressDTO);
        address.setUser(contextService.loadUserFromContext());
        address = saveAddress(address);
        return addressMapper.toDTO(address);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address address = findAddressById(addressId);
        if (!Objects.equals(address.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        Address updatedAddress = addressMapper.toEntity(addressDTO);
        updatedAddress.setId(addressId);
        updatedAddress.setCreatedDate(address.getCreatedDate());
        updatedAddress.setUser(address.getUser());
        updatedAddress = saveAddress(updatedAddress);
        return addressMapper.toDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = findAddressById(addressId);
        if (!Objects.equals(address.getUser().getId(), contextService.loadUserFromContext().getId())) {
            throw new NotAccessException();
        }
        User user = address.getUser();
        user.removeAddress(addressId);
        userService.saveUser(user);
        addressRepository.delete(address);
    }

    @Override
    public List<AddressDTO> findUserAddresses(Long userId) {
        User user = contextService.loadUserFromContext();
        if (!Objects.equals(user.getId(), userId)) {
            throw new NotAccessException();
        }
        return user.getAddresses().stream().map(addressMapper::toDTO).collect(Collectors.toList());
    }
}
