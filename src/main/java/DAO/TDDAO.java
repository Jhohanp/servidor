/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.conexion;
import iDAO.IBaseDatos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.TarjetaDebito;

/**
 *
 * @author jhopi
 */
public class TDDAO implements IBaseDatos<TarjetaDebito> {
    public boolean insertar(TarjetaDebito td) {
		boolean registrar = false;
		
		Statement stm= null;
		Connection con=null;
		
		String sql="INSERT INTO tarjetadebito values ('"+td.getDigitos()+"','"+td.getTipo()+"','"+td.getDisponible()+"','"+td.getCuota()+"','"+td.getDisponible()+"')";
		
		try {			
			con=conexion.conectar();
			stm= con.createStatement();
			stm.execute(sql);
			registrar=true;
			stm.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase TDDAO, método registrar");
			e.printStackTrace();
		}
		return registrar;
	}
 
	
	public ArrayList<TarjetaDebito> BuscarTodo() {
		Connection co =null;
		Statement stm= null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM tarjetadebito ORDER BY ID";
		
		ArrayList<TarjetaDebito> listaTD= new ArrayList<TarjetaDebito>();
		
		try {			
			co= conexion.conectar();
			stm=co.createStatement();
			rs=stm.executeQuery(sql);
			while (rs.next()) {
				TarjetaDebito s=new TarjetaDebito();
                                s.setTipo(rs.getString(2));
				s.setCuota(rs.getInt(4));
				s.setDigitos(rs.getInt(1));
                                s.setDisponible(rs.getInt(3));
                                s.setDinero(rs.getInt(5));
                               
                            
				listaTD.add(s);
			}
			stm.close();
			rs.close();
			co.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase TDDAO, método obtener"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		
		return listaTD;
	}
 
public ArrayList<TarjetaDebito> Buscar(String id) throws SQLException {
		Connection co =null;
		Statement stm= null;
		ResultSet rs=null;
	
		String sql="SELECT * FROM tarjetadebito where ID ="+id;
		ArrayList<TarjetaDebito> lista= new ArrayList<TarjetaDebito>();
		
		
		try {			
			co= conexion.conectar();
			stm=co.createStatement();
			rs=stm.executeQuery(sql);
			while (rs.next()) {
				TarjetaDebito s=new TarjetaDebito();
                                s.setTipo(rs.getString(2));
				s.setCuota(rs.getInt(4));
				s.setDigitos(rs.getInt(1));
                                s.setDisponible(rs.getInt(3));
                               
                            
				lista.add(s);
			}
			stm.close();
			rs.close();
			co.close();
		} catch (SQLException s) {
			System.out.println("Error: Clase EfectivoDAO, método obtener");
			s.printStackTrace();
		}
		
		return lista;
	}
	public boolean actualizar(TarjetaDebito td) {
		Connection connect= null;
		Statement stm= null;
		
		boolean actualizar=false;
				
		String sql="UPDATE tarjetadebito SET ID="+td.getDigitos()+", Tipo='"+td.getTipo()+"', Cuota="+td.getCuota()+", Disponible="+td.getDisponible()+"  WHERE ID="+td.getDigitos();
                System.out.println(sql);
		try {
			connect=conexion.conectar();
			stm=connect.createStatement();
			stm.execute(sql);
			actualizar=true;
		} catch (SQLException e) {
			System.out.println("Error: Clase TDDAO, método actualizar");
			e.printStackTrace();
		}		
		return actualizar;
	}
 

	public boolean eliminar(TarjetaDebito td) {
		Connection connect= null;
		Statement stm= null;
		
		boolean eliminar=false;
				
		String sql="DELETE FROM TarjetaDebito WHERE Digitos="+td.getDigitos();
		try {
			connect=conexion.conectar();
			stm=connect.createStatement();
			stm.execute(sql);
			eliminar=true;
		} catch (SQLException e) {
			System.out.println("Error: Clase TDDAO, método eliminar");
			e.printStackTrace();
		}		
		return eliminar;
	}
}
