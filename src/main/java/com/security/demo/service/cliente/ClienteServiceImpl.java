package com.security.demo.service.cliente;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.security.demo.entity.Cliente;
import com.security.demo.entity.request.ClienteRequest;
import com.security.demo.entity.response.ClienteResponse;
import com.security.demo.map.Map;
import com.security.demo.repository.ClienteRepository;
import com.security.demo.securityException.CpfJaCadastradoException;
import com.security.demo.securityException.EmailJaCadastradoEncotradaException;
import com.security.demo.securityException.EntidadeNaoEncotradaException;
import com.security.demo.util.page.ClientePageable;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private ClienteRepository clienteRep;

	@Autowired
	private Map<Cliente, ClienteResponse> responseClientMap;

	@Autowired
	private Map<ClienteRequest, Cliente> clienteMap;

	@Transactional
	@Override
	public ClienteResponse create(ClienteRequest clienteRequest) {
		LOGGER.info("Criando um registro de Cliente");
		Assert.notNull(clienteRequest, "ProdutoRequest inválida, null !");
		Cliente cliente = clienteMap.converter(clienteRequest);
		isEmailValide(cliente.getEmail());
		isCpfValide(cliente.getCpf());

		return clienteRep.saveAndFlush(cliente)
				.map(client -> responseClientMap.converter(client));
	}

	

	@Override
	public List<ClienteResponse> buscarTodosClientes() {

		return null;
	}

	@Override
	public Page<ClienteResponse> getClientePage(ClientePageable clietPage) {
		LOGGER.info("Buscando um registro de Cliente Page");
		Assert.notNull(clietPage, "Request inválida, null !");
		Sort sort = Sort.by(clietPage.getSortDirection(), clietPage.getSortBy());
		Pageable pageable = PageRequest.of(clietPage.getPageNumber(), clietPage.getSizePage(), sort);

		return clienteRep.findAll(pageable).map(cliente -> this.responseClientMap.converter(cliente));
	}

	@Transactional
	@Override
	public ClienteResponse updateCliente(Long id, ClienteRequest clienteRequest) {
		LOGGER.info("Uppdate um registro de Cliente");
		Assert.notNull(clienteRequest, "Request inválida, null !");
		
		Cliente cliente = getClienteId(id);
		Cliente updateCliente = mergeCliente(clienteRequest, cliente);
		
		return clienteRep.saveAndFlush(updateCliente).map(prodUpd -> this.responseClientMap.converter(updateCliente));
	}

	@Transactional
	@Override
	public boolean deleteCliente(Long id) {
		LOGGER.info("Deletando um registro do tipo Cliente");
		Assert.notNull(id, "ID inválido !");

		getClienteId(id);
		clienteRep.deleteById(id);
		return true;

//	LOGGER.warn("Erro ao remover o registro {}", id);

	}

	@Override
	public ClienteResponse buscarUmCliente(Long id) {
		LOGGER.info("Buscando um registro tipo Cliente pelo Id");

		return clienteRep.findById(id).map(cliente -> this.responseClientMap.converter(cliente)).orElseThrow(
				() -> new EntidadeNaoEncotradaException(String.format("Cliente não encontrado, com Id %d !", id)));
	}

	@Override
	public ClienteResponse buscarClienteNome(String nome) {
		LOGGER.info("Buscando um registro tipo Cliente pelo Nome");

		return clienteRep.findByNomeContaining(nome.strip()).stream().findFirst()
				.map(x -> this.responseClientMap.converter(x)).orElseThrow(() -> new EntidadeNaoEncotradaException(
						String.format("Cliente não encontrado, com Nome %s !", nome)));
	}

	public Cliente getClienteId(Long id) {
		return clienteRep.findById(id).orElseThrow(
				() -> new EntidadeNaoEncotradaException(String.format("Produto não encontrado, com Id %d !", id)));
	}


	public void isEmailValide(String email) {
		if (clienteRep.existsByEmail(email)) {
			throw new EmailJaCadastradoEncotradaException(String.format("E-mail %s já Cadastrado!", email));
		}
	}

	public void isCpfValide(String cpf) {
		if (clienteRep.existsByCpf(cpf)) {
			throw new CpfJaCadastradoException(String.format("Cpf %s já Cadastrado!", cpf));

		}
	}

	public Cliente mergeCliente(ClienteRequest clienteRequest, Cliente cliente) {

		if (clienteRequest.getNome() != null) {
			cliente.setNome(clienteRequest.getNome());
		}

		if (clienteRequest.getEmail() != null) {
			isEmailValide(clienteRequest.getEmail().strip());
			cliente.setEmail(clienteRequest.getEmail());
		}
		if (clienteRequest.getCpf() != null) {
			isCpfValide(clienteRequest.getCpf().strip());
			cliente.setCpf(clienteRequest.getCpf());
		}
		if (clienteRequest.getTelefone() != null) {
			cliente.setTelefone(clienteRequest.getTelefone());
		}

		if (clienteRequest.getBairro() != null) {
			cliente.getEndereco().setBairro(clienteRequest.getBairro());
		}
		if (clienteRequest.getCidade() != null) {
			cliente.getEndereco().setCidade(clienteRequest.getCidade());
		}
		if (clienteRequest.getLogradouro() != null) {
			cliente.getEndereco().setLogradouro(clienteRequest.getLogradouro());
		}
		if (clienteRequest.getCep() != null) {
			cliente.getEndereco().setCep(clienteRequest.getCep());
		}
		if (clienteRequest.getNumero() != null) {
			cliente.getEndereco().setNumero(clienteRequest.getNumero());
		}
		
		return cliente;
	}

	
}
