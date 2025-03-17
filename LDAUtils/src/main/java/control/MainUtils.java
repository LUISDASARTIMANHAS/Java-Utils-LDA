/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class MainUtils {

//    metodo/função de validar cpf
    public static boolean ValidarCPF(String cpf) {
        String CpfFormatado = cpf.replace(".", "").replace("-", "");
        int TamCPF = CpfFormatado.length();

        for (int i = 0; i < 10; i++) {
            String CpfIgual = GerarCpfIgual(i);
            //percorre o tanto de cpf com numeros iguais
            if (CpfFormatado.equals(CpfIgual)) {
                System.err.println("CPF IGUAL DETECTADO: CPF: "
                        + CpfFormatado
                        + " E IGUAL A CPF GERADO: "
                        + CpfIgual);
                return (false);
            }
        }
        if (TamCPF != 11) {
            System.err.println("CPF TEM TAMANHOS INCORRETOS! " + TamCPF + ", cpf: " + CpfFormatado);
            return false;
        }
        if (!ValidarDigitoVerificador(CpfFormatado)) {
            System.err.println("DIGITO VERIFICADOR DO CPF FALHOU!");
            return false;
        }
        return true;
    }

    private static String GerarCpfIgual(int i) {
        String CpfIgual = "";

        for (int j = 0; j < 11; j++) {
            //percorre o tamanho do cpf incrementando a posicao do i
            //Ex: se i=0 então ele roda 11 vezes o que da 00000000000
            CpfIgual = CpfIgual + i;
        }
//        System.out.println("CPF IGUAL GERADO: " + CpfIgual);
        return CpfIgual;
    }

    public static boolean ValidarDigitoVerificador(String cpf) {
        char Digver1, Digver2;
        int sm, r, num, peso;

        sm = 0;
        peso = 10;
        for (int i = 0; i < 9; i++) {
            num = (int) (cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso--;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11)) {
            Digver1 = '0';
        } else {
            Digver1 = (char) (r + 48);
        }

//        calculo do segundo digito
        sm = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            num = (int) (cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso--;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11)) {
            Digver2 = '0';
        } else {
            Digver2 = (char) (r + 48);
        }

        if ((Digver1 == cpf.charAt(9)) && (Digver2 == cpf.charAt(10))) {
            return true;
        } else {
            return false;
        }
    }

    public static Endereco consultarCEP(String cep) throws MalformedURLException, IOException {

        Endereco ender = null;

        // Definir a URL do serviço ViaCEP
        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");

        // Definir a URL do serviço GOV.BR
        //URL url = new URL("https://h-apigateway.conectagov.estaleiro.serpro.gov.br/api-cep/v1/consulta/cep/" + cep);
        // Abrir conexão HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Ler a resposta
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // Converter a resposta JSON em um objeto JSONObject
        JSONObject jsonObject = new JSONObject(response.toString());

        // Exibir as informações do endereço
        if (!jsonObject.has("erro")) {

            ender = new Endereco();
            ender.setLogradouro(jsonObject.getString("logradouro"));
            ender.setBairro(jsonObject.getString("bairro"));
            ender.setCidade(jsonObject.getString("localidade"));
            ender.setUf(jsonObject.getString("uf"));

        } else {
            System.out.println("CEP não encontrado.");

        }

        // Fechar conexão
        connection.disconnect();
        return ender;

    }

}
