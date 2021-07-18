package com.security.demo.service.cliente;

import java.util.List;

import org.springframework.data.domain.Page;

import com.security.demo.entity.request.ClienteRequest;
import com.security.demo.entity.response.ClienteResponse;
import com.security.demo.util.page.ClientePageable;

public interface ClienteService {

	ClienteResponse create(ClienteRequest clienteRequest);
	
	Page<ClienteResponse> getClientePage(ClientePageable clientePageable);
	
	ClienteResponse updateCliente(Long id, ClienteRequest clienteRequest);
	
	boolean deleteCliente(Long id);

	List<ClienteResponse> buscarTodosClientes();
	
	ClienteResponse buscarUmCliente(Long id);
	
	ClienteResponse buscarClienteNome(String nome);
   
	

}
