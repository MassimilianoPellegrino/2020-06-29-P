/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<String> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	this.txtResult.clear();
    	List<DefaultWeightedEdge> result;
    	try {
    		result = model.archiPesoMassimo();
    		for(DefaultWeightedEdge e: result)
    			this.txtResult.appendText(e.toString()+"\n");
    	}catch(NullPointerException e) {
    		e.printStackTrace();
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	int min;
    	String mese;
    	try {
    		min = Integer.parseInt(this.txtMinuti.getText());
    		mese = this.cmbMese.getValue();
    		model.creaGrafo(min, mese);
    		this.txtResult.appendText("Grafo creato con "+model.getGrafo().vertexSet().size()+" vertici e "+model.getGrafo().edgeSet().size()+" archi");
    		
    		this.btnConnessioneMassima.setDisable(false);
        	this.btnCollegamento.setDisable(false);
        	
        	this.cmbM1.getItems().addAll(model.getGrafo().vertexSet());
        	this.cmbM2.getItems().addAll(model.getGrafo().vertexSet());

    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero intero");    
    	}catch(NullPointerException e) {
    		this.txtResult.setText("Selezionare un mese dal menu");
    	}
    	
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	this.txtResult.clear();
    	List<Match> result;
    	Match m1;
    	Match m2;
    	try {
    		m1 = this.cmbM1.getValue();
    		m2 = this.cmbM2.getValue();
    		result = model.getCammino(m1, m2);
    		if(result.size()>0) {
    			for(Match m: result)
    				this.txtResult.appendText(m+"\n");
    		}else
    			this.txtResult.appendText("Collegamento inesistente");
    	}catch(NullPointerException e) {
    		this.txtResult.appendText("Selezionare due match dai menu");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbMese.getItems().add("Agosto");
    	this.cmbMese.getItems().add("Settembre");
    	this.cmbMese.getItems().add("Ottobre");
    	this.cmbMese.getItems().add("Novembre");
    	this.cmbMese.getItems().add("Dicembre");
    	this.cmbMese.getItems().add("Gennaio");
    	this.cmbMese.getItems().add("Febbraio");
    	this.cmbMese.getItems().add("Marzo");
    	this.cmbMese.getItems().add("Aprile");
    	this.cmbMese.getItems().add("Maggio");
    	this.btnConnessioneMassima.setDisable(true);
    	this.btnCollegamento.setDisable(true);
    }
    
    
}
