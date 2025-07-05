package com.kradev.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.excepciones.KrakeDevException;
import com.krakedev.inventario.entidades.Cliente;
import com.krakedev.utils.ConexionBDD;

public class ClientesBDD {
	public void insertar(Cliente cliente) throws KrakeDevException {
		Connection con = null;

		try {
			con = ConexionBDD.obtenerConexion();
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO clientes (cedula, nombre, numerohijos) VALUES (?, ?, ?)");
			ps.setString(1, cliente.getCedula());
			ps.setString(2, cliente.getNombre());
			ps.setInt(3, cliente.getNumeroHijos());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar cliente. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void actualizar(Cliente cliente) throws KrakeDevException {
		Connection con = null;

		try {
			con = ConexionBDD.obtenerConexion();
			PreparedStatement ps = con
					.prepareStatement("UPDATE clientes SET nombre = ?, numerohijos = ? WHERE cedula = ?");
			ps.setString(1, cliente.getNombre());
			ps.setInt(2, cliente.getNumeroHijos());
			ps.setString(3, cliente.getCedula());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al actualizar cliente. Detalle: " + e.getMessage());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Cliente> recuperarTodos() throws KrakeDevException {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cedula, nombre, numerohijos from clientes");
			rs = ps.executeQuery();

			while (rs.next()) {
				String cedula = rs.getString("cedula");
				String nombre = rs.getString("nombre");
				int numeroHijos = rs.getInt("numerohijos");

				cliente = new Cliente(cedula, nombre, numeroHijos);
				clientes.add(cliente);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
		return clientes;
	}

	public Cliente buscarPorPk(String cedulaBusqueda) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cedula, nombre, numeroHijos from clientes where TRIM(cedula) = ?");

			System.out.println("Buscando cédula: [" + cedulaBusqueda + "]");

			ps.setString(1, cedulaBusqueda.trim());
			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("Existe el cliente");
				String cedula = rs.getString("cedula");
				String nombre = rs.getString("nombre");
				int numeroHijos = rs.getInt("numeroHijos");

				cliente = new Cliente(cedula, nombre, numeroHijos);
			} else {
				System.out.println("No existe el cliente para cédula: [" + cedulaBusqueda + "]");
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}

		return cliente;
	}

	public ArrayList<Cliente> buscarPorNumeroHijos(int numeroHijos) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Cliente> lista = new ArrayList<>();

		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("SELECT cedula, nombre, numeroHijos FROM clientes WHERE numeroHijos >= ?");
			ps.setInt(1, numeroHijos);
			rs = ps.executeQuery();

			while (rs.next()) {
				String cedula = rs.getString("cedula").trim();
				String nombre = rs.getString("nombre");
				int hijos = rs.getInt("numeroHijos");
				Cliente c = new Cliente(cedula, nombre, hijos);
				lista.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al buscar clientes: " + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return lista;
	}

}
