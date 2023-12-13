package ecommerce.project.backend.mappers;

import ecommerce.project.backend.dto.AddressDTO;
import ecommerce.project.backend.entities.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper {
    private final ModelMapper modelMapper;

    public Address toEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO, Address.class);
    }

    public AddressDTO toDTO(Address address) {
        return modelMapper.map(address, AddressDTO.class);
    }
}
