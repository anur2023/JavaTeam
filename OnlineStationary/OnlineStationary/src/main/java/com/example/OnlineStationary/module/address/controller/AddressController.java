package com.example.OnlineStationary.address.controller;

import com.example.OnlineStationary.address.dto.request.AddressRequest;
import com.example.OnlineStationary.address.dto.response.AddressResponse;
import com.example.OnlineStationary.address.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) { this.addressService = addressService; }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(@RequestParam Long userId,
                                                      @Valid @RequestBody AddressRequest req) {
        return ResponseEntity.ok(addressService.addAddress(userId, req));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Long userId) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId));
    }
}