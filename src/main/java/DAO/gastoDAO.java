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
import java.util.List;
import model.Efectivo;
import model.Gasto;
import model.TarjetaCredito;
import model.TarjetaDebito;

/**
 *
 * @author jhopi
 */
public class gastoDAO implements IBaseDatos<Gasto>  {
     EfectivoDAO daoefectivo = new EfectivoDAO();
    TCDAO TCD = new TCDAO();
    TDDAO TDD = new TDDAO();
	

    
    public List<Gasto> BuscarTodo() throws SQLException {
		Connection co =null;
		Statement stm= null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM Gasto ORDER BY CodGasto";
		
		List<Gasto> listaGasto= new ArrayList<Gasto>();
		
		try {			
			co= conexion.conectar();
			stm=co.createStatement();
			rs=stm.executeQuery(sql);
			while (rs.next()) {
				Gasto g=new Gasto();
				g.setCategoria(rs.getString(1));
				g.setCodGasto(rs.getString(2));
				g.setFecha(rs.getString(3));
				g.setFrecDiaria(rs.getInt(4));
                                g.setHora(rs.getString(5));
                                g.setMontoGasto(rs.getInt(6));
                                
                                if (rs.getBoolean(7)== true){
                                g.setMetodoPago("true");
                                }else if (rs.getString(8) != null) {
                                g.setMetodoPago(rs.getString(8));
                            }else if (rs.getString(9) != null) {
                                g.setMetodoPago(rs.getString(9));
                            }
                                
                                
                                
				listaGasto.add(g);
			}
			stm.close();
			rs.close();
			co.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método obtener");
			e.printStackTrace();
		}
		
		return listaGasto;
	}

    public ArrayList<Gasto> BuscarDia(String fecha) throws SQLException {
		Connection co =null;
		Statement stm= null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM Gasto where Fecha='"+fecha+"'";
		      System.out.println(sql);
		ArrayList<Gasto> listaGasto= new ArrayList<Gasto>();
		
		try {			
			co= conexion.conectar();
			stm=co.createStatement();
			rs=stm.executeQuery(sql);
			while (rs.next()) {
				Gasto g=new Gasto();
				g.setCategoria(rs.getString(5));
				g.setCodGasto(rs.getString(1));
				g.setFecha(rs.getString(3));
				g.setFrecDiaria(rs.getInt(6));
                                g.setHora(rs.getTime(4)+"");
                                g.setMontoGasto(rs.getInt(2));
                                g.setDescripcion(rs.getString(7));
				listaGasto.add(g);
			}
			stm.close();
			rs.close();
			co.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método obtener");
			e.printStackTrace();
		}
		
		return listaGasto;
	}
        public ArrayList<Gasto> BuscarGastosentre(String fechai, String fechaf) throws SQLException {
		Connection co =null;
		Statement stm= null;
		ResultSet rs=null;
		
		String sql="SELECT * FROM Gasto where Fecha between '"+fechai+"' and '"+fechaf+"'";
		
		ArrayList<Gasto> listaGasto= new ArrayList<Gasto>();
		
		try {			
			co= conexion.conectar();
			stm=co.createStatement();
			rs=stm.executeQuery(sql);
			while (rs.next()) {
				Gasto g=new Gasto();
				g.setCategoria(rs.getString(1));
				g.setCodGasto(rs.getString(2));
				g.setFecha(rs.getString(3));
				g.setFrecDiaria(rs.getInt(4));
                                g.setHora(rs.getString(5));
                                g.setMontoGasto(rs.getInt(6));
                                g.setMetodoPago(rs.getString(7));
                                
				listaGasto.add(g);
			}
			stm.close();
			rs.close();
			co.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método obtener");
			e.printStackTrace();
		}
		
		return listaGasto;
	}
    public boolean insertar(Gasto g) throws SQLException {
        boolean registrar = false;
        String sql=null;
        String identificador = g.getMetodoPago().split("#")[1].split(" ")[1];
        String id =g.getMetodoPago().split("#")[1].split(" ")[0];
		      System.out.println(identificador);
                      System.out.println(id);
		Statement stm= null;
		Connection con=null;
		      if (identificador.equals("0")) {
                    sql="insert into gasto values ('"+g.getCodGasto()+"','"+g.getMontoGasto()+"','"+g.getFecha()+"','"+g.getHora()+"','"+g.getCategoria()+"','"+g.getFrecDiaria()+"','"+g.getDescripcion()+"',"+1+","+null+","+null+")";       
                        ArrayList<Efectivo> aux=daoefectivo.Buscar(1); 
                         aux.get(0).setMonto(aux.get(0).getMonto()-g.getMontoGasto());
                        daoefectivo.actualizar(aux.get(0));
                      } else if (identificador.equals("1")) {
                         ArrayList<TarjetaCredito> aux=TCD.Buscar(id); 
                         aux.get(0).setCupo(aux.get(0).getCupo()-g.getMontoGasto());
                        TCD.actualizar(aux.get(0));
                    sql="insert into gasto values ('"+g.getCodGasto()+"','"+g.getMontoGasto()+"','"+g.getFecha()+"','"+g.getHora()+"','"+g.getCategoria()+"','"+g.getFrecDiaria()+"','"+g.getDescripcion()+"',"+0+","+id+","+null+")";       
                    } else if (identificador.equals("2")) {
                         ArrayList<TarjetaDebito> aux=TDD.Buscar(id); 
                          System.out.println(aux.get(0).getDisponible());
                          aux.get(0).setDisponible(aux.get(0).getDisponible()-g.getMontoGasto());
                        TDD.actualizar(aux.get(0));
                    sql="insert into gasto values ('"+g.getCodGasto()+"','"+g.getMontoGasto()+"','"+g.getFecha()+"','"+g.getHora()+"','"+g.getCategoria()+"','"+g.getFrecDiaria()+"','"+g.getDescripcion()+"',"+0+","+null+","+id+")";        
                    }
		try {			
			con=conexion.conectar();
			stm= con.createStatement();
			stm.execute(sql);
			registrar=true;
			stm.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método registrar");
			e.printStackTrace();
		}
		return registrar;
    }

   
    public boolean actualizar(Gasto gasto) throws SQLException {
       Connection connect= null;
		Statement stm= null;
		
		boolean actualizar=false;
				
		String sql="UPDATE Gasto SET Categoria='"+gasto.getCategoria()+"', CodGasto='"+gasto.getCodGasto()+"', MontoGasto='"+gasto.getMontoGasto()+"', Fecha ='"+gasto.getFecha()+"',Hora='"+gasto.getHora()+"', FrecDiaria ='"+gasto.getFrecDiaria()+"',MetodoPago='"+gasto.getMetodoPago()+"' WHERE CodGasto="+gasto.getCodGasto();
		try {
			connect=conexion.conectar();
			stm=connect.createStatement();
			stm.execute(sql);
			actualizar=true;
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método actualizar");
			e.printStackTrace();
		}		
		return actualizar;
    }

    
    public boolean eliminar(Gasto gasto) throws SQLException {
        Connection connect= null;
		Statement stm= null;
		
		boolean eliminar=false;
				
		String sql="DELETE FROM Gasto WHERE CodGasto="+gasto.getCodGasto();
		try {
			connect=conexion.conectar();
			stm=connect.createStatement();
			stm.execute(sql);
			eliminar=true;
		} catch (SQLException e) {
			System.out.println("Error: Clase gastoDAO, método eliminar");
			e.printStackTrace();
		}		
		return eliminar;
    }
}
