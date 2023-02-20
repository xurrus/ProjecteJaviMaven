package exercici4;

import com.mongodb.ServerAddress;
import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Exercici_4_a {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		String pais;
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		
		//recollim pais
		System.out.println("Introdueix nom d'un pais:");
		pais = teclado.nextLine();
		
		//filtrem
		List<Document> documents = new ArrayList<Document>();
		coleccio.find(eq("pais", pais))
		.projection(fields(include("cod","nom"),excludeId()))
		.into(documents);

		if(documents.size() > 0) {
			System.out.println("*** GRUPS DEL PAIS "+pais+": ***");
			for (Document d : documents) {
				System.out.println("\t-"+ d.get("nom") + " (cod "+d.get("cod")+")");
			}
		}else {
			System.out.println("No hi ha cap grup del pais "+pais);
		}
		teclado.close();
	}
}
