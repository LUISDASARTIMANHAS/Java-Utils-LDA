package control;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 * Modelo de tabela genérico (AutoTableModel) que usa reflexão para extrair automaticamente
 * os métodos getters da classe T e construir as colunas da tabela dinamicamente,
 * ignorando getters que retornam tipos complexos para evitar mostrar objetos inteiros na tabela.
 * 
 * @author LUIS DAS ARTIMANHAS
 * 
 * USAGE:
 * private AutoTableModel<ItemPedido> autoTable;
 * autoTable = new AutoTableModel<>(ItemPedido.class);
 * tblPedido.setModel(autoTable);
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

    // Conjunto de tipos simples permitidos para exibição na tabela
    private static final Set<Class<?>> TIPOS_PERMITIDOS = new HashSet<>(Arrays.asList(
        String.class,
        int.class, Integer.class,
        long.class, Long.class,
        double.class, Double.class,
        float.class, Float.class,
        boolean.class, Boolean.class,
        char.class, Character.class,
        short.class, Short.class,
        byte.class, Byte.class
    ));

    /**
     * Construtor: recebe a classe T e identifica seus getters publicamente acessíveis,
     * filtrando apenas aqueles que retornam tipos simples permitidos.
     */
    public AutoTableModel(Class<T> clazz) {
        for (Method method : clazz.getMethods()) {
            // Verifica se o método é um getter válido E retorna tipo simples
            if (isGetter(method) && retornaTipoPermitido(method)) {
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
     * Verifica se o método getter retorna um tipo simples permitido.
     */
    private boolean retornaTipoPermitido(Method method) {
        Class<?> tipoRetorno = method.getReturnType();
        return TIPOS_PERMITIDOS.contains(tipoRetorno);
    }

    @Override
    public int getRowCount() {
        return dados.size();
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
        T obj = dados.get(rowIndex);
        try {
            return getters.get(columnIndex).invoke(obj);
        } catch (Exception e) {
            return "Erro " + e;
        }
    }

    /**
     * Adiciona um novo item à tabela
     */
    public void adicionar(T obj) {
        dados.add(obj);
        fireTableRowsInserted(dados.size() - 1, dados.size() - 1);
    }

    /**
     * Remove um item da tabela pelo índice
     */
    public void remover(int indice) {
        dados.remove(indice);
        fireTableRowsDeleted(indice, indice);
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
