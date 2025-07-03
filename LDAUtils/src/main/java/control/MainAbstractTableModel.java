/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public abstract class MainAbstractTableModel extends AbstractTableModel {

    // Lista com os dados que serão exibidos na tabela
    private List<Object> listaItens = new ArrayList<>();

    // Lista de métodos getters identificados na classe T
    private List<Method> getters = new ArrayList<>();

    // Nomes das colunas (derivados do nome dos getters)
    private List<String> nomesColunas = new ArrayList<>();

//    construtores e getters and setters da classe
    public MainAbstractTableModel() {
    }

    public List<Method> getGetters() {
        return getters;
    }

    public void setGetters(List<Method> getters) {
        this.getters = getters;
    }

    public List<String> getNomesColunas() {
        return nomesColunas;
    }

    public void setNomesColunas(List<String> nomesColunas) {
        this.nomesColunas = nomesColunas;
    }

//    metodos herdados com comportamento default
    @Override
    public int getRowCount() {
        return listaItens.size();
    }

    @Override
    public int getColumnCount() {
        return getters.size();
    }

    @Override
    public String getColumnName(int column) {
        return nomesColunas.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = listaItens.get(rowIndex);
        try {
            return getters.get(columnIndex).invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return "Erro " + e;
        }
    }

//    metodos para interação na tabela
    /**
     * Adiciona um novo item à tabela
     */
    public void adicionar(Object obj) {
        listaItens.add(obj);
        fireTableRowsInserted(listaItens.size() - 1, listaItens.size() - 1);
    }

    /**
     * Remove um item da tabela pelo índice
     */
    public void remover(int indice) {
        listaItens.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }

    /**
     * Retorna o item da linha especificada
     */
    public Object getItem(int i) {
        return listaItens.get(i);
    }

    public void setLista(List novaLista) {
        if (novaLista == null || novaLista.isEmpty()) {
            if (!listaItens.isEmpty()) {
                listaItens.clear();
                fireTableRowsDeleted(0, 0);
            }
        } else {
            listaItens = novaLista;
            fireTableRowsInserted(0, listaItens.size() - 1);
        }

    }

    public void clearLista() {
        listaItens.clear();
        fireTableRowsDeleted(0, 0);

    }

    /**
     * Retorna a lista completa de dados
     */
    public List<Object> getLista() {
        return listaItens;
    }

}
