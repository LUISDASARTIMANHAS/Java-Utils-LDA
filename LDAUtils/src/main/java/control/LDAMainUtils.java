/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONObject;

/**
 * Classe utilitária contendo métodos para carregar arquivos, redimensionar
 * imagens, e consultar o serviço de CEP (ViaCEP).
 *
 * @author LUIS DAS ARTIMANHAS
 */
public class LDAMainUtils {

    /**
     * Abre uma janela de diálogo para o usuário selecionar um arquivo, com um
     * filtro de extensão.
     *
     * @param filtro Filtro para tipos de arquivo (por exemplo, imagens,
     * documentos).
     * @param comp Componente pai para a janela de diálogo.
     * @param dir Diretório inicial onde o JFileChooser abrirá (pode ser null
     * para usar o diretório padrão).
     * @return O arquivo selecionado pelo usuário, ou null se o usuário
     * cancelar.
     */
    public static File carregarArq(FileNameExtensionFilter filtro, Component comp, File dir) {
        // Cria uma nova janela de diálogo para escolher arquivos
        JFileChooser fileWindow = new JFileChooser();

        // Configurações para a janela de seleção de arquivos
        fileWindow.setMultiSelectionEnabled(false); // Permite selecionar apenas um arquivo
        fileWindow.setAcceptAllFileFilterUsed(false); // Desativa o filtro de todos os arquivos
        fileWindow.setFileFilter(filtro); // Aplica o filtro de tipo de arquivo

        // Se um diretório foi passado, abre a janela no diretório informado
        if (dir != null) {
            fileWindow.setCurrentDirectory(dir);
        }

        // Exibe a janela de seleção de arquivos e verifica se o usuário selecionou um arquivo
        if (fileWindow.showOpenDialog(comp) == JFileChooser.APPROVE_OPTION) {
            File arq = fileWindow.getSelectedFile(); // Pega o arquivo selecionado
            return arq;
        } else {
            return null; // Retorna null caso o usuário cancele a seleção
        }
    }

    /**
     * Redimensiona uma imagem para ajustá-la às dimensões de um JLabel.
     *
     * @param arq O arquivo de imagem a ser redimensionado.
     * @param labelFoto O JLabel no qual a imagem será exibida.
     * @return Um objeto ImageIcon contendo a imagem redimensionada.
     */
    public static ImageIcon redimensionarImg(File arq, JLabel labelFoto) {
        ImageIcon foto = new ImageIcon(arq.getPath()); // Cria um ImageIcon a partir do arquivo

        // Obtém a imagem e redimensiona para o tamanho do JLabel
        Image imagem = foto.getImage();
        Image Scale = imagem.getScaledInstance(labelFoto.getWidth(), labelFoto.getHeight(), Image.SCALE_DEFAULT);
        foto.setImage(Scale); // Atualiza a imagem com o novo tamanho
        return foto;
    }

    /**
     * Consulta o serviço ViaCEP para obter informações sobre um endereço com
     * base no CEP.
     *
     * @param cep O CEP a ser consultado.
     * @param endereco
     * @return Um objeto EnderecoCep contendo as informações do endereço, ou
     * null se o CEP não for encontrado.
     * @throws MalformedURLException Se a URL do serviço ViaCEP for malformada.
     * @throws IOException Se houver problemas de conexão ou leitura da
     * resposta.
     */
    public static EnderecoCep consultarCEP(String cep) throws MalformedURLException, IOException {
        EnderecoCep ender = null;
        // Define a URL do serviço ViaCEP com o CEP fornecido
        URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");

        // Abrir uma conexão HTTP com o serviço
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET"); // Método GET para obter dados

        // Lê a resposta da API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line); // Concatena as linhas da resposta
        }

        // Converte a resposta JSON em um objeto JSONObject
        JSONObject jsonObject = new JSONObject(response.toString());

        // Verifica se a resposta contém erro
        if (!jsonObject.has("erro")) {
            // Cria um objeto EnderecoCep e preenche com as informações do JSON
            ender = new EnderecoCep(
                    jsonObject.getString("logradouro"),
                    jsonObject.getString("bairro"),
                    jsonObject.getString("localidade"),
                    jsonObject.getString("uf")
            );
        } else {
            System.out.println("CEP não encontrado.");
        }

        // Fecha a conexão
        connection.disconnect();
        return ender; // Retorna o objeto EnderecoCep ou null se não encontrado
    }

//  Important:
//  Ensure that the sound file format is supported by Java Sound (WAV, AIFF, AU). MP3 requires additional libraries.
//  Handle potential exceptions (UnsupportedAudioFileException, IOException, LineUnavailableException).
    public static synchronized void playSound(final String soundName) {
        new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
            @Override
            public void run() {
                try {
                    if (soundName == null) {
                        Toolkit.getDefaultToolkit().beep();
                    } else {

                        Clip clip = AudioSystem.getClip();
                        File file = new File("src/main/resources/" + soundName + ".wav").getAbsoluteFile();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
                        clip.open(inputStream);
                        clip.start();
                    }
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    public static float CalcValorTotal(float subTotal, float valorTotal) {
        return subTotal + valorTotal;
    }

    public static float CalcSubTotal(int qtde, float valor) {
        return qtde * valor;
    }

    public static void main(String[] args) {
        System.out.println("=====================");
        System.out.println("LDA UTILS LOADED!!");
        System.out.println("=====================");
    }
}
