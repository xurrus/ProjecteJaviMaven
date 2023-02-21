package exercici5;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;

public class Exercici_5_b {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		Integer id_grup;
		Document grup;
		String dni;
		boolean modificado = false;

		System.out.println("*** ANEM A CAMBIAR LA FUNCIO D'UN ARTISTA PER LA DE 'DOLÇAINER' ***");
		System.out.println("Introdueix codi de grup:");
		id_grup = teclado.nextInt();
		//busquem el grup
		List<Document> grups = new ArrayList<Document>();
		coleccio.find(eq("cod", id_grup)).into(grups);
		if(grups.size() > 0) {
			grup = grups.get(0);
			System.out.println("Introdueix DNI del artista:");
			dni = teclado.next();
			//obtinguem llista de artistes
			List<Document> artistes = new ArrayList<Document>();
			artistes = (List<Document>) grup.get("artistes");
			if(artistes.size() > 0) {
				for(Document a : artistes) {
					if(a.get("dni").equals(dni)) {
						//quan trobe el artista, li cambia la funcio i ixim de la llista de artistes
						a.put("funcio", "dolçainer");
						modificado = true;
						break;
					}
				}
			}else {
				System.out.println("El grup no te cap artista");
			}
			if(modificado) {
				UpdateResult updateador = coleccio.updateOne(eq("cod",id_grup), new Document("$set",new Document("artistes",artistes)));
				System.out.println("**ARTISTA MODIFICAT**");
			}else {
				System.out.println("No hay ningun artista con ese DNI en ese grupo");
			}
			
		}else {
			System.out.println("No existeix cap grup amb el codi "+id_grup);
		}
		teclado.close();
	}
}
