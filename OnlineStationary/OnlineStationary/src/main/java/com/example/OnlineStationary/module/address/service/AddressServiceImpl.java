package com.example.OnlineStationary.address.service;

import com.example.OnlineStationary.address.dto.request.AddressRequest;
import com.example.OnlineStationary.address.dto.response.AddressResponse;
import com.example.OnlineStationary.address.entity.Address;
import com.example.OnlineStationary.address.repository.AddressRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repo;

    public AddressServiceImpl(AddressRepository repo) { this.repo = repo; }

    @Override
    public AddressResponse addAddress(Long userId, AddressRequest req) {
        Address a = new Address();
        a.setStreet(req.getStreet()); a.setCity(req.getCity());
        a.setState(req.getState()); a.setPostalCode(req.getPostalCode());
        a.setCountry(req.getCountry()); a.setDefault(req.isDefault());
        a.setUserId(userId);
        return toResponse(repo.save(a));
    }

    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    private AddressResponse toResponse(Address a) {
        return new AddressResponse(a.getId(), a.getStreet(), a.getCity(),
                a.getState(), a.getPostalCode(), a.getCountry(), a.isDefault());
    }
}