/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bruni
 */
public class Produtos {
    private long id;
    private String nome;
    private double vlrUnit;
    double quantidadde;

    public double getQuantidadde() {
        return quantidadde;
    }

    public void setQuantidadde(double quantidadde) {
        this.quantidadde = quantidadde;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getVlrUnit() {
        return vlrUnit;
    }

    public void setVlrUnit(double vlrUnit) {
        this.vlrUnit = vlrUnit;
    }
}
