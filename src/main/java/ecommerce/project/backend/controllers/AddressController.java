package ecommerce.project.backend.controllers;

import ecommerce.project.backend.dto.AddressDTO;
import ecommerce.project.backend.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/addresses")
@RequiredArgsConstructor
@CrossOrigin(allowedHeaders = "*", origins = "*")
@Tag(name = "Address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("/user-addresses/{userId}")
    @Operation(summary = "Find all addresses of user by their own id")
    public ResponseEntity<Object> findUserAddresses(@PathVariable Long userId) {
        List<AddressDTO> userAddresses = addressService.findUserAddresses(userId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("userAddresses", userAddresses);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Create new address")
    public ResponseEntity<Object> createAddress(@RequestBody @Valid AddressDTO addressDTO) {
        AddressDTO newAddress = addressService.createAddress(addressDTO);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("newAddress", newAddress);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/update/{addressId}")
    @Operation(summary = "Update user's address")
    public ResponseEntity<Object> updateAddress(@PathVariable Long addressId, @RequestBody @Valid AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("updatedAddress", updatedAddress);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{addressId}")
    @Operation(summary = "Delete a user's address")
    public ResponseEntity<Object> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", true);
        resp.put("msg", "Delete address successfully!");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
