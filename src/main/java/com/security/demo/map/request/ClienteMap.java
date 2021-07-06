package com.security.demo.map.request;

import org.springframework.stereotype.Component;

import com.security.demo.entity.Cliente;
import com.security.demo.entity.Endereco;
import com.security.demo.entity.request.ClienteRequest;
import com.security.demo.map.Map;
import com.security.demo.securityException.EntidadeVaziaException;

@Component
public class ClienteMap implements Map<ClienteRequest, Cliente>{

	@Override
	public Cliente converter(ClienteRequest clienteRequest) {
if (clienteRequest == null) {
	throw new EntidadeVaziaException("Cliente está vázio");
}

Cliente cliente = new Cliente();
cliente.setNome(clienteRequest.getNome());
cliente.setEmail(clienteRequest.getEmail());
cliente.setCpf(clienteRequest.getCpf());
cliente.setTelefone(clienteRequest.getTelefone());
cliente.setEndereco(getEndereco(clienteRequest));

		
return cliente;
	}

	public Endereco getEndereco(ClienteRequest enderecoRequest) {
		Endereco endereco = new Endereco();
		
		endereco.setBairro(enderecoRequest.getBairro());
		endereco.setCidade(enderecoRequest.getCidade());
		endereco.setCep(enderecoRequest.getCep());
		endereco.setLogradouro(enderecoRequest.getLogradouro());
		endereco.setNumero(enderecoRequest.getNumero());
		
		return endereco;
	}

}
