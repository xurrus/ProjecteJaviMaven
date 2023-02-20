package exercici4;

import static com.mongodb.client.model.Filters.all;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


public class Exercici_4_c {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		String companyiaT,nom_companyia="";
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("companyies");
		
		
		//Llista de discos (nom del disc i data de llançament) que ha publicat
		//una companyia donada per l'usuari. 
		
		//recollim companyia
		System.out.println("Introdueix nom d'una companyia:");
		companyiaT = teclado.nextLine();
		//filtrem companyia 
		List<Document> companyies = new ArrayList<Document>();
		coleccio.find(eq("nom", companyiaT))
		.projection(fields(include("nom","discs.nom","discs.dia" ),excludeId()))
		.into(companyies);
		
		if(companyies.size() > 0) {
			System.out.println("*** DISCOS DE LA COMPANYIA "+companyiaT+": ***");
			for (Document c : companyies) {
				nom_companyia = (String) c.get("nom");
				List<Document> discs = new ArrayList<Document>();
				discs = (List<Document>) c.get("discs");
				if(discs.size() > 0) {
					for(Document d : discs) {
						System.out.println("\t-" + d.get("nom") + ". Dia " + d.get("dia"));
					}
				}else {
					System.out.println("La companyia " + nom_companyia + " no te discs");
				}
			}
			
		}else {
			System.out.println("La companyia "+companyiaT+" no existeix");
		}
		teclado.close();
	}
}
