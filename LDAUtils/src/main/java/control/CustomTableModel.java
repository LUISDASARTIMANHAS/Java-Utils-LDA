/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
/**
 * Modelo de tabela genérico baseado em lista de métodos getters informada
 * manualmente. Ideal para quando você quer escolher a ordem ou quais colunas
 * exibir.
 *
 * Exemplo de uso: List<String> getters = List.of("getNome", "getIdade",
 * "getCpf"); tabela.setModel( getters,
 * MinhaClasse.class));
 *
 * @param <T> Tipo dos objetos da lista
 */

// USAGE
//List<ItemPedido> lista = getItens();
//List<String> getters = List.of("getDescricao", "getQuantidade", "getPreco");
//
//CustomTableModel<ItemPedido> modelo = new CustomTableModel<>(lista, getters, ItemPedido.class);
//tabela.setModel(modelo);

public class CustomTableModel<T> extends AbstractTableModel {

    // Lista que contém os itens exibidos na tabela.
    private List listaItens = new ArrayList();
    private final List<Method> getters = new ArrayList<>();
    private final List<String> nomesColunas = new ArrayList<>();

    public CustomTableModel(List<String> nomesMetodosGetter, Class<T> clazz) {

        for (String nomeMetodo : nomesMetodosGetter) {
            try {
                Method m = clazz.getMethod(nomeMetodo);
                getters.add(m);
                 System.out.println("ADD GETTERS CUSTOM TABLE: " + m);

                String nomeColuna = nomeMetodo.replaceFirst("get", "");
                nomeColuna = Character.toUpperCase(nomeColuna.charAt(0)) + nomeColuna.substring(1);
                nomesColunas.add(nomeColuna);
                System.out.println("ADD COLUMN CUSTOM TABLE: " + nomeColuna);
                System.out.println("ADD COLUMN CUSTOM TABLE: " + nomeColuna);
            } catch (NoSuchMethodException e) {   
                nomesColunas.add("Inválido");
                getters.add(null);
            }
        }
    }

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
        T obj = (T) listaItens.get(rowIndex);
        try {
            Method metodo = getters.get(columnIndex);
            return metodo.invoke(obj);
        } catch (Exception e) {
            return "Erro " + e;
        }
    }

    /**
     * Retorna o item presente na linha especificada.
     *
     * @param rowIndex O índice da linha.
     * @return O item presente na linha especificada.
     */
    public T getItem(int rowIndex) {
        return (T) listaItens.get(rowIndex);
    }

    /**
     * Adiciona um item à lista e notifica a tabela sobre a inserção de uma nova linha.
     *
     * @param obj O item a ser adicionado à lista.
     */
    public void adicionar(T obj) {
        listaItens.add(obj);
        fireTableRowsInserted(listaItens.size() - 1, listaItens.size() - 1);
    }

    /**
     * Remove um item da lista com base no índice especificado e notifica a tabela sobre a remoção da linha.
     *
     * @param indice O índice do item a ser removido.
     */
    public void remover(int indice) {
        listaItens.remove(indice);
        fireTableRowsDeleted(indice, indice);
    }

    /**
     * Edita um item existente na lista. Primeiro remove o item no índice fornecido e depois adiciona o item editado.
     *
     * @param obj    O item editado a ser adicionado.
     * @param indice O índice do item a ser editado.
     */
    public void editar(T obj, int indice) {
        remover(indice);
        adicionar(obj);
    }

    /**
     * Retorna a lista atual de itens.
     *
     * @return A lista de itens.
     */
    public List<T> getLista() {
        return listaItens;
    }
}
