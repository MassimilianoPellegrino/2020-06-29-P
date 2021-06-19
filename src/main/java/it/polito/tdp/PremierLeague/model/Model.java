package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	Graph<Match, DefaultWeightedEdge> grafo;
	Map<Integer, Match> idMap;
	PremierLeagueDAO dao = new PremierLeagueDAO();
	List<Match> bestSol;
	double bestPeso;

	public void creaGrafo(int min, String mese) {
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getMonthMatches(mese, idMap));
		List<Arco> archi = dao.getArchi(min);
		
		for(Arco a: archi)
			if(idMap.containsKey(a.getIdMatch1()) && idMap.containsKey(a.getIdMatch2()) && a.getPeso()>0)
				Graphs.addEdgeWithVertices(grafo, idMap.get(a.getIdMatch1()), idMap.get(a.getIdMatch2()), a.getPeso());
		
		//System.out.println(grafo.vertexSet().size()+"\t"+grafo.edgeSet().size());
		
	}
	
	public List<DefaultWeightedEdge> archiPesoMassimo() {
		
		List<DefaultWeightedEdge> result = new ArrayList<>();
		
		double max = 0;
		
		for(DefaultWeightedEdge edge: grafo.edgeSet())
			if(grafo.getEdgeWeight(edge)>max)
				max = grafo.getEdgeWeight(edge);
		
		for(DefaultWeightedEdge edge: grafo.edgeSet())
			if(grafo.getEdgeWeight(edge)==max)
				result.add(edge);
		
		/*for(DefaultWeightedEdge e: result)
			System.out.println(e+" "+grafo.getEdgeWeight(e));*/
				
		return result;
			
	}

	public Graph<Match, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Match> getCammino(Match m1, Match m2){
		bestSol = new LinkedList<>();
		bestPeso = 0;
		
		double pesoParziale = 0;
		LinkedList<Match> solParziale = new LinkedList<>();
		solParziale.add(m1);
		itera(m2, solParziale, pesoParziale);
		
		/*for(Match m: bestSol)
			System.out.println(m);*/
		
		return bestSol;
	}

	private void itera(Match m2, LinkedList<Match> solParziale, double pesoParziale) {
		
		if(solParziale.getLast().equals(m2)) {
			if(pesoParziale>bestPeso) {
				bestSol = new LinkedList<>(solParziale);
				bestPeso = pesoParziale;
				System.out.println("barabba");
			}
			return;
		}
		
		Match attuale = solParziale.getLast();
		
		for(Match m: Graphs.successorListOf(grafo, attuale)) {
			if(solParziale.contains(m) ||
					(m.getTeamAwayID().equals(attuale.getTeamAwayID()) && m.getTeamHomeID().equals(attuale.getTeamHomeID())) || 
					(m.getTeamAwayID().equals(attuale.getTeamHomeID()) && m.getTeamHomeID().equals(attuale.getTeamAwayID()))) {
				continue;
			}
			
			solParziale.add(m);
			pesoParziale += grafo.getEdgeWeight(grafo.getEdge(attuale, m));
			
			itera(m2, solParziale, pesoParziale);
			
			pesoParziale -= grafo.getEdgeWeight(grafo.getEdge(attuale, m));
			solParziale.remove(m);
		}
			
		
	}

	public Map<Integer, Match> getIdMap() {
		return idMap;
	}
	
	
}
