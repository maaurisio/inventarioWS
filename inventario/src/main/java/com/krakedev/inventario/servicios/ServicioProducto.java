package com.krakedev.inventario.servicios;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.kradev.persistencia.ClientesBDD;
import com.krakedev.inventario.entidades.Categoria;
import com.krakedev.inventario.entidades.Producto;

import java.util.ArrayList;
import java.util.List;

@Path("productos")
public class ServicioProducto {

	@GET
	public String mensaje() {
		return "Bienvenido a productos";
	}

	@Path("insertar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void insertarProducto(Producto producto) {
		System.out.println("------>insertar a: " + producto);
		/*
		ClientesBDD cli = new ClientesBDD();
		cli.insertar(producto);
		*/
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void actualizarProducto(Producto producto) {
		System.out.println("------>actualizar a: " + producto);
	}

	@Path("mostrarProductos")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> recuperarTodos() {
		List<Producto> productos = new ArrayList<>();

		productos.add(new Producto("A001", "Laptop", new Categoria(1, "Tecnologia"), 800.0, 5));
		productos.add(new Producto("B002", "Mouse", new Categoria(2, "Accesorios"), 20.0, 50));
		productos.add(new Producto("C003", "Monitor", new Categoria(1, "Tecnologia"), 150.0, 10));

		return productos;
	}
}
