/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import DAO.EfectivoDAO;
import DAO.TCDAO;
import DAO.TDDAO;
import DAO.gastoDAO;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jhopi
 */
public class Negocio {
     gastoDAO daogasto = new gastoDAO();
    EfectivoDAO daoefectivo = new EfectivoDAO();
    TCDAO daoTC = new TCDAO();
    TDDAO daoTD = new TDDAO();
    public void guardarGasto(String json) throws SQLException{
        Gasto gasto = convertGastoFromJson(json);
        daogasto.insertar(gasto);
    }
    public void guardarEfectivo(String json) throws SQLException {       
        Efectivo efectivo = convertEfectivoFromJson(json);
        daoefectivo.insertar(efectivo);
        
    }
    public void guardarTC(String json) throws SQLException {
        TarjetaCredito TC = convertTCFromJason(json);
        daoTC.insertar(TC);
    }
    public void guardarTD(String json) throws SQLException {
        TarjetaDebito TD = convertTDFromJson(json);
        daoTD.insertar(TD); 
    }
    private Gasto convertGastoFromJson(String json){
        Gson gson = new Gson();
        Gasto gasto = gson.fromJson(json, Gasto.class);
        return gasto;
    }
    public ArrayList<MetodoPago> TraerMetodosPago() throws SQLException {
        ArrayList<MetodoPago> metodos = new ArrayList<>();
             metodos.add(new MetodoPago("Efectivo", "",0));
             
        ArrayList<TarjetaCredito> Tcs = daoTC.BuscarTodo();
        for (int i = 0; i < Tcs.size(); i++) {
           MetodoPago met = new MetodoPago(Tcs.get(i).getTipo(), Tcs.get(i).getDigitos()+"",1);
           metodos.add(met);
            System.out.println("AQUQUQUUQ"+met.identificador);
        }
        ArrayList<TarjetaDebito> Tds = daoTD.BuscarTodo();
        for (int i = 0; i < Tds.size(); i++) {
           MetodoPago met = new MetodoPago(Tds.get(i).getTipo(), Tds.get(i).getDigitos()+"",2);
           metodos.add(met);
            System.out.println("AQUIIII"+met.identificador);

        }
        System.out.println(metodos.size());
       return metodos;
    }

    private Efectivo convertEfectivoFromJson(String json) {
        Gson gson = new Gson();
        Efectivo efectivo = gson.fromJson(json, Efectivo.class);
        return efectivo;
    }

    private TarjetaDebito convertTDFromJson(String json) {
        Gson gson = new Gson();
        TarjetaDebito TD = gson.fromJson(json, TarjetaDebito.class);
        return TD;
    }

    private TarjetaCredito convertTCFromJason(String json) {
        Gson gson = new Gson();
        TarjetaCredito TC = gson.fromJson(json, TarjetaCredito.class);
        return TC;
    }
    public ArrayList<Gasto> consultarDia(String fecha){
            try {
                ArrayList<Gasto> gastos=daogasto.BuscarDia(fecha);
                return gastos;
            } catch (SQLException ex) {
                Logger.getLogger(Negocio.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
}
