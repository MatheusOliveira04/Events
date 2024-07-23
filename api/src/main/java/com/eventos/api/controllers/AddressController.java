package com.eventos.api.controllers;

import com.eventos.api.models.address.Address;
import com.eventos.api.models.address.AddressDTO;
import com.eventos.api.services.Impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @Secured({"ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable UUID id) {
        Address address = addressService.findById(id);
        return ResponseEntity.ok(new AddressDTO(address.getId(), address.getCity(), address.getUf()));
    }

    @Secured({"ROLE_USER"})
    @Cacheable(value = "/address/findAll")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.addressService.findAll(pageable)
                        .stream()
                        .map(dto -> new AddressDTO(dto.getId(), dto.getCity(), dto.getUf()))
                        .toList());
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/address/findAll", allEntries = true)
    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO addressDTO, UriComponentsBuilder uriBuilder) {
        this.addressService.insert(new Address(null, addressDTO.city(), addressDTO.uf()));
        URI uri = uriBuilder.path("address/{id}").buildAndExpand(addressDTO.id()).toUri();
        return ResponseEntity.created(uri).body(addressDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/address/findAll", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@RequestBody AddressDTO addressDTO, @PathVariable UUID id) {
        this.addressService.update(new Address(id, addressDTO.city(), addressDTO.uf()));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(id).toUri();
        return ResponseEntity.ok().location(uri).body(addressDTO);
    }

    @Secured({"ROLE_ADMIN"})
    @CacheEvict(value = "/address/findAll", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
