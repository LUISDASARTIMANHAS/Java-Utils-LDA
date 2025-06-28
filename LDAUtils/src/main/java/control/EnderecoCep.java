package control;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class EnderecoCep implements Serializable {

    private static final long serialVersionUID = 1L;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public EnderecoCep(String logradouro, String bairro, String cidade, String uf) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }
    
}
