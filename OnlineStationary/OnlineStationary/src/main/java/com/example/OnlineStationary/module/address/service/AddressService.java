package com.example.OnlineStationary.address.service;

import com.example.OnlineStationary.address.dto.request.AddressRequest;
import com.example.OnlineStationary.address.dto.response.AddressResponse;
import java.util.List;

public interface AddressService {
    AddressResponse addAddress(Long userId, AddressRequest request);
    List<AddressResponse> getAddressesByUserId(Long userId);
}