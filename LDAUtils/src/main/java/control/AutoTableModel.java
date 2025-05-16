package control;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabela genérico (AutoTableModel) que usa reflexão para extrair automaticamente
 * os métodos getters da classe T e construir as colunas da tabela dinamicamente.
 * @author LUIS DAS ARTIMANHAS
 * 
 * @param <T> Tipo de objeto que será exibido na tabela
 */
public class AutoTableModel<T> extends AbstractTableModel {

    // Lista com os dados que serão exibidos na tabela
    private final List<T> dados = new ArrayList<>();

    // Lista de métodos getters identificados na classe T
    private final List<Method> getters = new ArrayList<>();

    // Nomes das colunas (derivados do nome dos getters)
    private final List<String> nomesColunas = new ArrayList<>();

    /**
     * Construtor: recebe a classe T e identifica seus getters publicamente acessíveis
     */
    public AutoTableModel(Class<T> clazz) {
        for (Method method : clazz.getMethods()) {
            // Verifica se o método é um getter válido
            if (isGetter(method)) {
                getters.add(method);

                // Gera o nome da coluna com base no nome do método
                String nome = method.getName().replaceFirst("get", "");
                nome = Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
                nomesColunas.add(nome);
            }
        }
    }

    /**
     * Verifica se um método segue o padrão de getter:
     * - público
     * - sem parâmetros
     * - nome começa com "get"
     * - não é o método getClass()
     */
    private boolean isGetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
               method.getParameterCount() == 0 &&
               method.getName().startsWith("get") &&
               !method.getName().equals("getClass");
    }

    /**
     * Retorna o número de linhas da tabela (quantidade de objetos na lista de dados)
     */
    @Override
    public int getRowCount() {
        return dados.size();
    }

    /**
     * Retorna o número de colunas (quantidade de getters encontrados)
     */
    @Override
    public int getColumnCount() {
        return getters.size();
    }

    /**
     * Retorna o nome da coluna (baseado no nome dos getters, formatado)
     */
    @Override
    public String getColumnName(int column) {
        return nomesColunas.get(column);
    }

    /**
     * Retorna o valor de uma célula específica da tabela,
     * usando o método getter correspondente via reflexão
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T obj = dados.get(rowIndex);
        try {
            return getters.get(columnIndex).invoke(obj); // Executa o getter
        } catch (Exception e) {
            return "Erro"; // Em caso de exceção, retorna string de erro
        }
    }

    /**
     * Adiciona um novo item à tabela
     */
    public void adicionar(T obj) {
        dados.add(obj);
        fireTableRowsInserted(dados.size() - 1, dados.size() - 1); // Notifica o JTable
    }

    /**
     * Remove um item da tabela pelo índice
     */
    public void remover(int indice) {
        dados.remove(indice);
        fireTableRowsDeleted(indice, indice); // Notifica o JTable
    }

    /**
     * Substitui completamente a lista de dados e atualiza a tabela
     */
    public void setDados(List<T> lista) {
        dados.clear();
        dados.addAll(lista);
        fireTableDataChanged(); // Notifica o JTable que os dados mudaram completamente
    }

    /**
     * Retorna o item da linha especificada
     */
    public T getItem(int i) {
        return dados.get(i);
    }

    /**
     * Retorna a lista completa de dados
     */
    public List<T> getDados() {
        return dados;
    }
}
