package com.krakedev.inventario.servicios;

import java.util.ArrayList;

import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kradev.persistencia.ClientesBDD;
import com.krakedev.excepciones.KrakeDevException;
import com.krakedev.inventario.entidades.Cliente;

@Path("clientes")
public class ServicioCliente {
	@Path("insertar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertarCliente(Cliente cliente) {
		System.out.println("------>insertar a: " + cliente);
		ClientesBDD cli = new ClientesBDD();
		try {
			cli.insertar(cliente);
			return Response.ok().build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizarCliente(Cliente cliente) {
		System.out.println("------> actualizar a: " + cliente);
		ClientesBDD cli = new ClientesBDD();
		try {
			cli.actualizar(cliente);
			return Response.ok("Cliente actualizado correctamente").build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().entity("Error al actualizar cliente").build();
		}
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)

	public Response obtenerClientes() {
		ClientesBDD cli = new ClientesBDD();
		ArrayList<Cliente> clientes = null;
		try {
			clientes = cli.recuperarTodos();
			return Response.ok(clientes).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

	@Path("buscarPorCedula/{cedulaParam}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorCedula(@PathParam("cedulaParam") String cedula) {
		ClientesBDD cli = new ClientesBDD();
		Cliente cliente = null;
		System.out.println("Ingresa: [" + cedula + "]");
		try {
			cliente = cli.buscarPorPk(cedula);
			return Response.ok(cliente).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("test/{param}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testParam(@PathParam("param") String param) {
		System.out.println("Test param: [" + param + "]");
		return "Recibido: " + param;
	}

	@Path("buscarPorNumeroHijos/{numeroHijos}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarClientesPorNumeroHijos(@PathParam("numeroHijos") int numeroHijos) {
		ClientesBDD cli = new ClientesBDD();
		ArrayList<Cliente> clientes = new ArrayList<>();
		System.out.println("Buscando clientes con número de hijos >= " + numeroHijos);

		try {
			clientes = cli.buscarPorNumeroHijos(numeroHijos);
			return Response.ok(clientes).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().entity("Error al buscar clientes").build();
		}
	}

}
