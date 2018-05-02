/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculoevolutivo;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;

/**
 *
 * @author v.tassinari
 */
public class Evolve {

    private static Evolve instance;
    private double parentFitness;
    private double fitness;
    private double cellsPop;
    double variacao=0;

    public static Evolve getInstance() {
        if (instance == null) {
            instance = new Evolve();
        }
        return instance;
    }

    /**
     *
     * @param quantity
     * @return a probabilidade de uma celula{@link Cell} se multiplicar
     * @see Cell
     */
    public double cellWeight(double quantity) {
        return quantity / cellsPop;
    }

    /**
     *
     * @param x
     * @return a avaliação de cada individuo {@link Cell}
     */
    public double evaluate(int x) {
        return -(Math.pow(3 * x, 4) - Math.pow(20 * x, 3) + Math.pow(42 * x, 2) - (36 * x) + 2);
    }

    /**
     *
     * @param population
     * @return a população gerada
     */
    public List<Cell> generatePop(List<Cell> population) {
        List<Cell> pop = new ArrayList<>();
        parentFitness = fitness;
        if (population.isEmpty()) {
            System.out.println("Iniciando população");
            cellsPop = 0;
            for (int x = 0; x <= 20; x++) {
                Cell unity = new Cell();
                unity.setValue(x);
                unity.setQuantity(Math.abs(evaluate(x - 10)));
                cellsPop += unity.getQuantity();
                population.add(unity);
            }
            population.forEach(cell -> {
                cell.setWeight(cellWeight(cell.getQuantity()));
            });
            return population;
        } else if (parentFitness == 0) {
            System.out.println("Criando primeira população");
            population.forEach((Cell cell) -> {

                int i = 0;
                while (i < (cell.getWeight() * 100)) {
                    if (pop.size() == 100) {
                        break;
                    } else {
                        fitness = cell.getQuantity();
                        pop.add(cell);
                        i++;
                    }
                }
//                    System.out.println(i);

            });
            fitness = fitness / pop.size();
            System.out.println(fitness);
        } else {
            System.out.println("Criando proxima geração");
//            System.out.println(population.size());
            Random r = new Random();
            byte[] unity1, unity2;
            int pos;
            while (population.size() >= 10) {
                if (population.size() == 0) {
                    pos = 0;
                } else {
                    pos = r.nextInt(population.size());
                }
                ByteBuffer bb = ByteBuffer.allocate(Integer.BYTES);
                bb.order(ByteOrder.BIG_ENDIAN);
                bb.putInt(population.get(pos).getValue());
                unity1 = bb.array();
//                System.out.println(bb.wrap(unity1).getInt());
                population.remove(pos);
                if (population.size() == 0) {
                    pos = 0;
                } else {
                    pos = r.nextInt(population.size());
                }
                bb = ByteBuffer.allocate(Integer.BYTES);
                bb.order(ByteOrder.BIG_ENDIAN);
                bb.putInt(population.get(pos).getValue());
                unity2 = bb.array();
                population.remove(pos);
//                System.out.println(bb.wrap(unity2).getInt());
                for (int i = 0; i < 2; i++) {
                    pos = r.nextInt(4);
                    byte aux = unity1[pos];
                    unity1[pos] = unity2[pos];
                    unity2[pos] = aux;
                }
                Cell unity = new Cell();
                unity.setValue(bb.wrap(unity1).getInt());
                unity.setQuantity(Math.abs(evaluate(unity.getValue() - 10)));
                cellsPop += unity.getQuantity();
                pop.add(unity);
                unity = new Cell();
                unity.setValue(bb.wrap(unity2).getInt());
                unity.setQuantity(Math.abs(evaluate(unity.getValue() - 10)));
                cellsPop += unity.getQuantity();
                pop.add(unity);
            }
            pop.forEach(cell -> {
                cell.setWeight(cellWeight(cell.getQuantity()));
            });
            parentFitness = fitness;
//            System.out.println(cellsPop);
//            System.out.println(pop.size());
            fitness = cellsPop / pop.size();
        }

        if (pop.isEmpty()) {
            return population;
        } else {
            return pop;
        }
    }
    
    public double desvioPadrão(List<Cell> pop){
        pop.forEach(cell->{
            variacao =variacao+Math.pow(cell.getQuantity()-fitness, 2);
        });
        variacao=variacao/pop.size();
        return Math.sqrt(variacao);
    }

    public void showPopulation(List<Cell> pop) {
        System.out.println("---Inicio geração---");
        pop.forEach(unity -> {
            System.out.println("Valor: " + unity.getValue() + " avaliação:"
                    + unity.getQuantity() + " probabilidade: " + unity.getWeight());
        });
        System.out.println("Fitness: " + fitness);
        System.out.println("===Fim geração===");
    }

    /**
     *
     * @return o fitness da população anterior
     */
    public double getParentFitness() {
        return parentFitness;
    }

    /**
     *
     * @param parentFitness
     */
    public void setParentFitness(double parentFitness) {
        this.parentFitness = parentFitness;
    }

    /**
     *
     * @return o fitness da população atual
     */
    public double getFitness() {
        return fitness;
    }

    /**
     *
     * @param fitness
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     *
     * @return o total de populações de individuos que podem ser gerados para
     * uma nova população
     */
    public double getCellsPop() {
        return cellsPop;
    }

    /**
     *
     * @param cellsPop
     */
    public void setCellsPop(double cellsPop) {
        this.cellsPop = cellsPop;
    }
}
