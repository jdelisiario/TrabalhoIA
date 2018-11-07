package br.edu.ifmg.bambui.computacao.ia.ag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    static double[][] matriz;
    static int numIter = 0;

    public static Individuo escolhaViciada(ArrayList<Individuo> populacao, double totalFitness) throws IOException {
        double r = Math.random();
        double b = 0.0;

        //shuffle embaralha a população 
        Collections.shuffle(populacao);

        for (int i = 0; i < populacao.size(); i++) {
            //O b é a razão do indivíduo; a proporção que ele ocupa na roleta
            b += 1- populacao.get(i).fitness(matriz) / totalFitness;

            //se o número aletório gerado for menor que o b significa que esse indivíduo vai ser escolhido
            if (r < b) {
                return populacao.get(i);
            }
        }

        throw new RuntimeException();

    }

    public static void main(String[] args) throws IOException {
        String arquivoCSV = "psin.csv";
        matriz = new double[1000][5];
        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {
            br = new BufferedReader(new FileReader(arquivoCSV));
            for (int k = 0; k < 1000; k++) {
                linha = br.readLine();
                String[] pais = linha.split(csvDivisor);
                matriz[k][0] = Double.parseDouble(pais[0]);
                matriz[k][1] = Double.parseDouble(pais[1]);
                matriz[k][2] = Double.parseDouble(pais[2]);
                matriz[k][3] = Double.parseDouble(pais[3]);
                matriz[k][4] = Double.parseDouble(pais[4]);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        //Para criar uma populaçao de indivíduos
        ArrayList<Individuo> populacao = new ArrayList<>();

        //Cria um vetor com a população de 300 indivíduos
        for (int i = 0; i < 500; i++) {
            populacao.add(new Individuo());
        }

        //Precisamos de um laço infinito para ser o laço de avaliação
        for(int iteracoes = 0; iteracoes<3000; iteracoes++) { //while(true){
            //devemos criar uma roleta viciada
            //indivíduos com maior fitness tendem a ser selecionados mais vezes
            //qto menor o fitness é melhor para nosso problema
            ArrayList<Individuo> novaPopulacao = new ArrayList<>();
            double totalFitness = 0;

            //soma-se todos para depois, pela porcentagem, viciar os dados 
            for (int i = 0; i < 500; i++) {
                totalFitness += populacao.get(i).fitness(matriz); 
            }

            //Onde será gerada a nova população através do cruzamento entre o pai e a mãe escolhidos
            for (int i = 0; i < 500; i++) {
                Individuo pai = escolhaViciada(populacao, totalFitness);
                Individuo mae = escolhaViciada(populacao, totalFitness);

                //variável corte vai falar qtos genes pegará do pai e qtos genes vai pegar da mãe
                //Pode ser colocado valor aleatório, mas nesse caso resolveu
                //se utilizar valor fixo
                int corte = 2;
                double[] novoCromossomo = new double[4];

                //Um laço for para fazer a copia do lado do pai e outro for para fazer a parte da mãe
                for (int j = 0; j < corte; j++) {
                    novoCromossomo[j] = pai.getCromossomo()[j];
                    
                    if (Math.random() < .02) {
                        novoCromossomo[j] = (novoCromossomo[j] + (double) (Math.random() * 4)) % 4;
                    }
                }

                for (int j = corte; j < 4; j++) {
                    novoCromossomo[j] = mae.getCromossomo()[j];

                    //operador de mutação; no caso desse exemplo é mutação aleatória
                    if (Math.random() < .02) {
                        novoCromossomo[j] = (novoCromossomo[j] + (double) (Math.random() * 4)) % 4;
                    }

                }
                //Substituindo os dois laços for acima pode ser feito como a seguir:
                //for(int j = 0; j < 8; j++) { novoCromossomo[j] = j < corte ? pai.getCromossomo()[j] : mae.getCromossomo()[j]; }

                novaPopulacao.add(new Individuo(novoCromossomo));
            }

            Individuo melhor = null;

            //Busca na amostra o melhor fitness
            for (int i = 0; i < 500; i++) {
                if (melhor == null || novaPopulacao.get(i).fitness(matriz) < melhor.fitness(matriz)) {
                    melhor = novaPopulacao.get(i);
                }
            }

            //Nesse momento tem-se o melhor indivíduo na nova populaçao gerada
            System.out.println("Melhor indivíduo desta geração: " + melhor + " com fitness " + melhor.fitness(matriz));

            populacao = novaPopulacao;
        }

    }

}
