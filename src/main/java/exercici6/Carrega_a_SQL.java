package exercici6;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hibernate.UtilesHibernate;
import pojos.Artista;
import pojos.Canso;
import pojos.Club;
import pojos.Companyia;
import pojos.Disc;
import pojos.Esta;
import pojos.Grup;
import pojos.Pertany;

public class Carrega_a_SQL {

	public static void main(String[] args) {
		//LLISTA DE DOCUMENTS PER A OBTINDRE DADES DE MONGO
		List<Document> docCompanyies= new ArrayList<Document>();
		List<Document> docGrups = new ArrayList<Document>();
		//LLISTA DE OBJECTES
		List<Artista> llArtista = new ArrayList<Artista>();
		List<Canso> llCanso = new ArrayList<Canso>();
		List<Club> llClub = new ArrayList<Club>();
		List<Companyia> llCompanyia = new ArrayList<Companyia>();
		List<Disc> llDisc = new ArrayList<Disc>();
		List<Grup> llGrup = new ArrayList<Grup>();
		List<Pertany> llPertany = new ArrayList<Pertany>();
		List<Esta> llEsta = new ArrayList<Esta>();
		//ATRIBUTS PER A OMPLIR OBJECTES
		Integer cod,num=0;
		String nom,funcio,dia,pais,seu,dni=null;
		
		//OBRIM SESIO DE MONGO DB
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		//PLENEM DOCUMENTS GRUPS
		coleccio.find().projection(fields(excludeId())).into(docGrups);
		//PLENEM DOCUMENTS COMPANYIES
		coleccio = basedades.getCollection("companyies");
		coleccio.find().projection(fields(excludeId())).into(docCompanyies);
		

		//OMPLIM LLISTA OBJECTES GRUP, OBJECTES ARTISTA, OBJECTES CLUB
		for(Document docgrup : docGrups) {
			//RECOLLIM OBJ GRUP
			cod = (Integer) docgrup.get("cod");
			nom = (String) docgrup.get("nom");
			dia = (String) docgrup.get("dia");
			pais = (String) docgrup.get("pais");
			Grup grup = new Grup(cod,nom,dia,pais);
			llGrup.add(grup);
			System.out.println(grup);
			//ANEM A RECOLLIR OBJECTES CLUBS
			List<Document> docClubs = new ArrayList<Document>();
			docClubs = (List<Document>) docgrup.get("clubs");
			for(Document docclub : docClubs) {
				//RECOLLIM OBJECTE CLUB
				cod = (Integer) docclub.get("cod");
				nom = (String) docclub.get("nom");
				seu = (String) docclub.get("seu");
				num = (Integer) docclub.get("num");
				Club club = new Club(cod,nom,seu,num,grup);
				llClub.add(club);
				System.out.println(club);
			}
			//ANEM A RECOLLIR OBJECTES ARTISTES
			List<Document> docArtistes = new ArrayList<Document>();
			docArtistes = (List<Document>) docgrup.get("artistes");
			for(Document docartista : docArtistes) {
				//RECOLLIM OBJECTE ARTISTA
				dni = (String) docartista.get("dni");
				System.out.println(dni);
				System.out.println(nom);
				nom = (String) docartista.get("nom");
				Artista artista = new Artista(dni,nom);
				llArtista.add(artista);
				System.out.println(artista);
				//ARA, AL TINDRE ELS OBJECTES GRUP I ARTISTA, PODREM CREAR PERTANY
				funcio = (String) docartista.get("funcio");
				Pertany pertany = new Pertany(artista,grup,funcio);
				System.out.println(pertany);
			}
		}
		
		
		
		
		
		
		
		
		//OBRIM SESIO SQL
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		sesion.getTransaction().commit();
	}
}
