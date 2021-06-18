package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	Graph<Match, DefaultWeightedEdge> grafo;
	Map<Integer, Match> idMap;
	PremierLeagueDAO dao = new PremierLeagueDAO();

	public void creaGrafo(int min, String mese) {
		idMap = new HashMap<>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getMonthMatches(mese, idMap));
		List<Arco> archi = dao.getArchi(min);
		
		for(Arco a: archi)
			if(idMap.containsKey(a.getIdMatch1()) && idMap.containsKey(a.getIdMatch2()) && a.getPeso()>0)
				Graphs.addEdgeWithVertices(grafo, idMap.get(a.getIdMatch1()), idMap.get(a.getIdMatch2()), a.getPeso());
		
		System.out.println(grafo.vertexSet().size()+"\t"+grafo.edgeSet().size());
		
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
		
		for(DefaultWeightedEdge e: result)
			System.out.println(e+"\t"+grafo.getEdgeWeight(e));
		
		return result;
			
	}
	
	
	
}
