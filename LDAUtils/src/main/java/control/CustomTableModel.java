/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.lang.reflect.Method;
import java.util.List;

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

public class CustomTableModel<Object> extends MainAbstractTableModel {

    public CustomTableModel(List<String> nomesMetodosGetter, Class<Object> clazz) {

        for (String nomeMetodo : nomesMetodosGetter) {
            try {
                Method m = clazz.getMethod(nomeMetodo);
                super.getGetters().add(m);
                 System.out.println("ADD GETTERS CUSTOM TABLE: " + m);

                String nomeColuna = nomeMetodo.replaceFirst("get", "");
                nomeColuna = Character.toUpperCase(nomeColuna.charAt(0)) + nomeColuna.substring(1);
                super.getNomesColunas().add(nomeColuna);
                System.out.println("ADD COLUMN CUSTOM TABLE: " + nomeColuna);
                System.out.println("ADD COLUMN CUSTOM TABLE: " + nomeColuna);
            } catch (NoSuchMethodException e) {   
                super.getNomesColunas().add("Inválido");
                super.getGetters().add(null);
            }
        }
    }

    /**
     * Edita um item existente na lista. Primeiro remove o item no índice fornecido e depois adiciona o item editado.
     *
     * @param obj    O item editado a ser adicionado.
     * @param indice O índice do item a ser editado.
     */
    public void editar(Object obj, int indice) {
        super.remover(indice);
        super.adicionar(obj);
    }
}
