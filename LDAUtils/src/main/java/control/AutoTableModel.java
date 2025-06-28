package control;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class AutoTableModel<T> extends MainAbstractTableModel {
    
    // Lista com os dados que serão exibidos na tabela
    private final List<T> dados = new ArrayList<>();

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
                super.getGetters().add(method);

                // Gera o nome da coluna com base no nome do método
                String nome = method.getName().replaceFirst("get", "");
                nome = Character.toUpperCase(nome.charAt(0)) + nome.substring(1);
               super.getNomesColunas().add(nome);
            }
        }
    }

    

    /**
     * Verifica se o método getter retorna um tipo simples permitido.
     */
    private boolean retornaTipoPermitido(Method method) {
        Class<?> tipoRetorno = method.getReturnType();
        return TIPOS_PERMITIDOS.contains(tipoRetorno);
    }

    /**
     * Verifica se um método segue o padrão de getter:
     * - público
     * - sem parâmetros
     * - nome começa com "get"
     * - não é o método getClass()
     */
    protected boolean isGetter(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
               method.getParameterCount() == 0 &&
               method.getName().startsWith("get") &&
               !method.getName().equals("getClass");
    }

   
}
