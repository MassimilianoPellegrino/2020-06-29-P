package it.polito.tdp.PremierLeague.model;

public class Arco {
	
	private int idMatch1;
	private int idMatch2;
	private int peso;
	public Arco(int idMatch1, int idMatch2, int peso) {
		super();
		this.idMatch1 = idMatch1;
		this.idMatch2 = idMatch2;
		this.peso = peso;
	}
	public int getIdMatch1() {
		return idMatch1;
	}
	public int getIdMatch2() {
		return idMatch2;
	}
	public int getPeso() {
		return peso;
	}
	
	

}
