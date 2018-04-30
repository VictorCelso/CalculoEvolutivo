/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculoevolutivo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v.tassinari
 */
public class CalculoEvolutivo {
    private static Evolve doTheEvolution;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        doTheEvolution = Evolve.getInstance();
        List<Cell> pop = new ArrayList<>();
        while((doTheEvolution.getParentFitness()-doTheEvolution.getFitness())<0.2){
            pop=doTheEvolution.generatePop(pop);
            doTheEvolution.showPopulation(pop);
        }
        
        
    }
    
    
    
}
