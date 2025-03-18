/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabela personalizado para exibir uma lista de itens em uma JTable.
 * Esta classe estende AbstractTableModel e permite operações como adicionar, remover e editar itens na tabela.
 * 
 * @author LUIS DAS ARTIMANHAS
 */
public class LDATableModelListaItens extends AbstractTableModel {
    
    // Lista que contém os itens exibidos na tabela.
    private List listaItens = new ArrayList();

    /**
     * Retorna o número de linhas na tabela (tamanho da lista de itens).
     * 
     * @return O número de linhas (itens) na tabela.
     */
    @Override
    public int getRowCount() {
        return listaItens.size();
    }

    /**
     * Retorna o número de colunas na tabela (número fixo de 4 colunas).
     * 
     * @return O número de colunas da tabela (4 colunas fixas).
     */
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Retorna o valor de uma célula específica na tabela, com base no índice da linha e da coluna.
     * 
     * @param rowIndex O índice da linha.
     * @param columnIndex O índice da coluna.
     * @return O valor da célula na linha e coluna fornecidas.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Obtém o item na linha especificada.
        Object item = getItem(rowIndex);
        // Retorna o item (poderia ser melhorado para retornar um valor específico do item baseado na coluna)
        return item;
    }

    /**
     * Retorna o item presente na linha especificada.
     * 
     * @param rowIndex O índice da linha.
     * @return O item presente na linha especificada.
     */
    public Object getItem(int rowIndex) {
        return listaItens.get(rowIndex);
    }

    /**
     * Adiciona um item à lista e notifica a tabela sobre a inserção de uma nova linha.
     * 
     * @param obj O item a ser adicionado à lista.
     */
    public void adicionar(Object obj) {
        listaItens.add(obj);
        // Notifica que uma nova linha foi inserida na tabela.
        fireTableRowsInserted(listaItens.size() - 1, listaItens.size() - 1);
    }

    /**
     * Remove um item da lista com base no índice especificado e notifica a tabela sobre a remoção da linha.
     * 
     * @param indice O índice do item a ser removido.
     */
    public void remover(int indice) {
        listaItens.remove(indice);
        // Notifica que uma linha foi removida da tabela.
        fireTableRowsDeleted(indice, indice);
    }

    /**
     * Edita um item existente na lista. Primeiro remove o item no índice fornecido e depois adiciona o item editado.
     * 
     * @param obj O item editado a ser adicionado.
     * @param indice O índice do item a ser editado.
     */
    public void editar(Object obj, int indice) {
        // Remove o item antigo na posição especificada.
        remover(indice);
        // Adiciona o item editado.
        adicionar(obj);
    }

    /**
     * Substitui a lista atual de itens por uma nova lista fornecida. Se a nova lista for nula ou vazia, a lista atual é limpa.
     * 
     * @param novaLista A nova lista de itens a ser exibida na tabela.
     */
    public void setLista(List novaLista) {
        if (novaLista == null || novaLista.isEmpty()) {
            // Se a nova lista for nula ou vazia, limpa a lista atual e notifica a remoção.
            if (!listaItens.isEmpty()) {
                listaItens.clear();
                fireTableRowsDeleted(0, 0);
            }
        } else {
            // Substitui a lista atual pela nova lista e notifica a inserção.
            listaItens = novaLista;
            fireTableRowsInserted(0, listaItens.size() - 1);
        }
    }

    /**
     * Retorna a lista atual de itens.
     * 
     * @return A lista de itens.
     */
    public List getLista() {
        return listaItens;
    }
}
