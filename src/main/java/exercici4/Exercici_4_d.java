package exercici4;

import static com.mongodb.client.model.Filters.gt;
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

public class Exercici_4_d {

	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("companyies");
		
		
		// De les cançons que duren més de 4 minuts, mostrar el títol, la
		// duració, el nom del disc en el que es troben i la data en la que se
		// llançà el disc. 
		List<Document> documents = new ArrayList<Document>();
		coleccio.find(gt("discs.cansons.duracio",4))
		.projection(fields(include("discs.cansons.titol","discs.cansons.duracio","discs.nom","discs.dia"),excludeId()))
		.into(documents);

		System.out.println("*** CANÇONS QUE DUREN MES DE 4 MINUTS: ***");
		for (Document doc : documents) {
			List<Document> discs = new ArrayList<Document>();
			discs = (List<Document>) doc.get("discs");
			for(Document disc : discs) {
				List<Document> cansons = new ArrayList<Document>();
				cansons = (List<Document>) disc.get("cansons");
				for(Document canso : cansons) {
					if((Double) canso.get("duracio") > 4) {
						System.out.println("-CANSO "+canso.get("titol") + ":");
						System.out.println("\tDuracio: "+ canso.get("duracio"));
						System.out.println("\tDisc: "+ disc.get("nom"));
						System.out.println("\tLlançament disc: "+ disc.get("dia"));
						System.out.println();
					}
				}
			}
		}
	}

}
