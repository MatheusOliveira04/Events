package com.eventos.api.services.Impl;

import com.eventos.api.models.address.Address;
import com.eventos.api.repositories.AddressRepository;
import com.eventos.api.services.AddressService;
import com.eventos.api.services.exceptions.IntegrityViolationException;
import com.eventos.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> findAll(Pageable pageable) {
        Page<Address> addressList = this.addressRepository.findAll(pageable);
        if (addressList.isEmpty()){
            throw new ObjectNotFoundException("nothing address to find");
        }
        return addressList.toList();
    }

    @Override
    public Address findById(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Didn't find any register with id: " + id));
    }

    @Override
    public Address insert(Address address) {
        this.validations(address);
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        this.findById(address.getId());
        this.validations(address);
        return addressRepository.save(address);
    }

    @Override
    public void delete(UUID id) {
        addressRepository.delete(this.findById(id));
    }

    private void validations(Address address) {
        this.validCity(address);
        this.validUf(address);
    }

    private void validUf(Address address) {
        this.verifyIfUfIsNullThrowException(address);
        this.verifyIfUfNotHaveTwoCharactersThrowException(address);
    }

    private void verifyIfUfIsNullThrowException(Address address) {
        if(address.getUf() == null || address.getUf().isBlank()) {
            throw new IntegrityViolationException("Uf is null or empty");
        }
    }

    private void verifyIfUfNotHaveTwoCharactersThrowException(Address address) {
        if(address.getUf().trim().length() < 2) {
            throw new IntegrityViolationException("Insert a valid Uf with two characters");
        }
    }

    private void validCity(Address address) {
        this.verifyIfCityIsNullThrowException(address);
    }

    private void verifyIfCityIsNullThrowException(Address address){
        if(address.getCity() == null || address.getCity().isBlank()) {
            throw new IntegrityViolationException("City is null or empty");
        }
    }
}
