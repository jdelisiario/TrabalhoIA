/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifmg.bambui.computacao.ia.ag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Individuo {

   
    private double[] cromossomo;

    public Individuo() {
        cromossomo = new double[4];       
        for (int i = 0; i < 4; i++) {                           
            //cromossomo[i] = (int) (Math.random() * 4);
            cromossomo[i] = (double)((Math.random()*500) - (Math.random()*500));
        }
    }

    @Override
    public String toString() {
        String str = "(";
        for (int i = 0; i < 4; i++) {
            str += getCromossomo()[i];
            if (i < 3) {
                str += ",";
            }
        }
        str += ")";
        return str;
    }

    public double fitness(double[][] matriz) {        
        double eqm = 0;

        for (int i = 0; i < 1000; i++) {
            
            eqm += Math.pow(((getCromossomo()[0] * (matriz[i][0])
                    + getCromossomo()[1] * (matriz[i][1])
                    + getCromossomo()[2] * (matriz[i][2])
                    + getCromossomo()[3] * (matriz[i][3])) - (matriz[i][4])), 2);
        }
        eqm = Math.sqrt(eqm)/1000;        
        return eqm;
    }

    public double[] getCromossomo() {
        return cromossomo;
    }

    public Individuo(double[] cromossomo) {
        this.cromossomo = cromossomo;
    }

    /**
     * @param cromossomo the cromossomo to set
     */
    public void setCromossomo(double[] cromossomo) {
        this.cromossomo = cromossomo;
    }

}
