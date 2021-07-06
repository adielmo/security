package com.security.demo.map.response;

import org.springframework.stereotype.Component;

import com.security.demo.entity.Cliente;
import com.security.demo.entity.Endereco;
import com.security.demo.entity.request.ClienteRequest;
import com.security.demo.entity.response.ClienteResponse;
import com.security.demo.map.Map;
import com.security.demo.securityException.EntidadeVaziaException;

@Component
public class ClienteResponseMap implements Map<Cliente, ClienteResponse> {

	@Override
	public ClienteResponse converter(Cliente cliente) {
		if (cliente == null) {
			throw new EntidadeVaziaException("Cliente está vázio");
		}

		ClienteResponse clienteResponse = new ClienteResponse();
		clienteResponse.setId(cliente.getId());
		clienteResponse.setNome(cliente.getNome());
		clienteResponse.setEmail(cliente.getEmail());
		clienteResponse.setCpf(cliente.getCpf());
		clienteResponse.setTelefone(cliente.getTelefone());
		clienteResponse.setBairro(cliente.getEndereco().getBairro());
		clienteResponse.setCidade(cliente.getEndereco().getCidade());
		clienteResponse.setCep(cliente.getEndereco().getCep());
		clienteResponse.setLogradouro(cliente.getEndereco().getLogradouro());
		clienteResponse.setNumero(cliente.getEndereco().getNumero());

		return clienteResponse;
	}

}
