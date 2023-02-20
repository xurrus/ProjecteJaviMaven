package exercici4;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Exercici_4_e {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		
		//De cada grup, el nom del grup, nom dels discos que han publicat i
		//nom companyia amb la que publicaren cada disc
		
		//obtinguem tots els grups
		List<Document> grupos = new ArrayList<Document>();
		coleccio.find()
		.projection(fields(include("cod","nom"),excludeId()))
		.into(grupos);
		
		
		//obtinguem totes les companyies
		coleccio = basedades.getCollection("companyies");
		List<Document> companyies = new ArrayList<Document>();
		coleccio.find()
		.projection(fields(include("nom","discs.nom","discs.cod_gru"),excludeId()))
		.into(companyies);

		
		System.out.println("*** GRUP I ELS SEUS DISCS: ***");
		for (Document g : grupos) {
			int id_grup = g.getInteger("cod");
			System.out.println("-" + g.get("nom") + "(cod " + id_grup + "):");
			for(Document c : companyies) {
				List<Document> discs = new ArrayList<Document>();
				discs = (List<Document>) c.get("discs");
				for(Document d : discs) {
					if(d.getInteger("cod_gru") == id_grup) {
						System.out.println("\tDisc " + d.get("nom") + ". Companyia: " + c.get("nom"));
					}
				}
			}
			System.out.println();
		}
	}

}
