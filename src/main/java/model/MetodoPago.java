/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jhopi
 */
public class MetodoPago {
  String Nombre, Digitos;
    int identificador,Dinero;

    public MetodoPago(String Nombre, String Digitos, int identificador, int Dinero) {
        this.Nombre = Nombre;
        this.Digitos = Digitos;
        this.identificador = identificador;
        this.Dinero=Dinero;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    

    public MetodoPago() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDigitos() {
        return Digitos;
    }

    public void setDigitos(String Digitos) {
        this.Digitos = Digitos;
    }

    public int getDinero() {
        return Dinero;
    }

    public void setDinero(int Dinero) {
        this.Dinero = Dinero;
    }  
}
