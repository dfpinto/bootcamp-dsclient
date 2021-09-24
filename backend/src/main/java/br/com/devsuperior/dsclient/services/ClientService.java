package br.com.devsuperior.dsclient.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devsuperior.dsclient.dto.ClientDTO;
import br.com.devsuperior.dsclient.entities.Client;
import br.com.devsuperior.dsclient.repositories.ClientRepository;
import br.com.devsuperior.dsclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(Pageable pageRequest) {
		return clientRepository.findAll(pageRequest).map(x->new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		return new ClientDTO(clientRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("entity not found")));
	}

	@Transactional
	public void delete(Long id) {
		clientRepository.deleteById(id);
	}

	@Transactional
	public ClientDTO update(ClientDTO dto) {
		try {
		   Client entity = clientRepository.getById(dto.getId());
		   copyClientDtoToClient(dto, entity);
		   
		   return new ClientDTO(clientRepository.save(entity));
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Entity not found"); 
		}
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
	   Client entity = new Client();
	   copyClientDtoToClient(dto, entity);
	   
	   return new ClientDTO(clientRepository.save(entity));
	}

	private void copyClientDtoToClient(ClientDTO dto, Client entity) {
	   entity.setName(dto.getName());
	   entity.setCpf(dto.getCpf());
	   entity.setBirthDate(dto.getBirthDate());
	   entity.setChildren(dto.getChildren());
	   entity.setIncome(dto.getIncome());
	}
}
