/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class LDATableModelListaItens extends AbstractTableModel {

    private List listaItens = new ArrayList();

    @Override
    public int getRowCount() {
        return listaItens.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object item = getItem(rowIndex);
        switch (columnIndex) {
            case 0:
                return item;
            case 1:
                return item.getQdte();
            case 2:
                return item.getPeso();
            case 3:
                return item.getValor();
        }
        return null;
    }

    public Object getItem(int rowIndex) {
        return listaItens.get(rowIndex);
    }

    public void adicionar(Object obj) {
        listaItens.add(obj);
        fireTableRowsInserted(listaItens.size() - 1, listaItens.size() - 1);

    }

    public void remover(int indice) {
        listaItens.remove(indice);
        fireTableRowsDeleted(indice, indice);

    }

    public void editar(Object obj, int indice) {
        remover(indice);
        adicionar(obj);
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

    public List getLista() {
        return listaItens;
    }

}
