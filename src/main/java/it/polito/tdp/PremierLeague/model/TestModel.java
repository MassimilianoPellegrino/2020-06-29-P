package it.polito.tdp.PremierLeague.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Model model = new Model();
		model.creaGrafo(10, "Maggio");
		//model.creaGrafo(85, "Febbraio");
		//model.archiPesoMassimo();
		
		model.getCammino(model.getIdMap().get(58), model.getIdMap().get(22));
	}

}
