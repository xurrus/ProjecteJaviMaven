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


public class Exercici_4_b {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		String grupT,nom_grup="";
		int cod_grup=0;
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		
		//Títol i duració de totes les cançons d'un grup que indique l'usuari. 
		
		//recollim grup
		System.out.println("Introdueix nom d'un grup:");
		grupT = teclado.nextLine();
		//filtrem el grup i arrepleguem el id
		List<Document> grups = new ArrayList<Document>();
		coleccio.find(eq("nom", grupT))
		.projection(fields(include("cod","nom"),excludeId()))
		.into(grups);
		if(grups.size() > 0) {
			for (Document d : grups) {
				cod_grup = (Integer) d.get("cod");
				nom_grup = (String) d.get("nom");
			}
			
			coleccio = basedades.getCollection("companyies");
			//ara fem el exercici
			List<Document> documents = new ArrayList<Document>();
			coleccio.find(all("discs.cod_gru", cod_grup))
			.projection(fields(include("discs"),excludeId()))
			.into(documents);
			
			if(documents.size() > 0) {
				System.out.println("*** CANÇONS DEL GRUP "+nom_grup+" (cod "+cod_grup+"): ***");
				
				//afagem cada disc
				for(Document d : documents) {
					List<Document> llistaDiscs = new ArrayList<Document>();
					llistaDiscs = (List<Document>) d.get("discs");
					
					if(llistaDiscs.size() > 0) {
						for(Document disc : llistaDiscs) {
							//de cada disc afagem les cansons
							List<Document> llistaCansons = new ArrayList<Document>();
							llistaCansons = (List<Document>) disc.get("cansons");
							
							if(llistaCansons.size() > 0) {
								for(Document c : llistaCansons) {
									//de cada canso mostrem la info
									System.out.println("\t-Titol: "+(String) c.get("titol")+". Duracio: "+(Double) c.get("duracio"));
								}
							}
						}
					}else {
						System.out.println("El grup no te ningun disc");
					}
				}
			}else {
				System.out.println("No hi ha cap disc del grup "+nom_grup);
			}
		}else {
			System.out.println("El grup "+grupT+" no existeix");
		}
		teclado.close();
	}
}
