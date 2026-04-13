package com.example.OnlineStationary.module.address.service;

import com.example.OnlineStationary.module.address.dto.request.AddressRequest;
import com.example.OnlineStationary.module.address.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse addAddress(Long userId, AddressRequest request);
    List<AddressResponse> getAddressesByUserId(Long userId);
}