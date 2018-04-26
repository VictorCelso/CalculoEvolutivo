/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculoevolutivo;

/**
 *
 * @author v.tassinari
 */
public class Cell {
    private int value;
    private double quantity;
    private double weight;

    /**
     *
     * @return o valor inteiro do individuo
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value (valor inteiro do individuo)
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     *
     * @return a quantidade de individuos dentro de uma população deste tipo de individuo
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return a probabilidade deste individuo de se multiplicar
     */
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
}
