package interficies;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hibernate.UtilesHibernate;
import pojos.Club;
import pojos.Companyia;
import pojos.Disc;
import pojos.Esta;
import pojos.Grup;
import pojos.Pertany;

public class CarregaGrupsCompanyiesMongo {

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public static void main(String[] args) {
		// V
		List<Companyia> companyiesBD = new ArrayList<Companyia>();
		List<Grup> grupsBD = new ArrayList<Grup>();
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();

		//OBTINGUEM DADES DE LA SQL
		Query q = sesion.createQuery("SELECT g FROM Grup g");
		grupsBD = (List<Grup>) q.list();

		q = sesion.createQuery("SELECT comp FROM Companyia comp");
		companyiesBD = (List<Companyia>) q.list();

		//////////////////////////
		/////// GRUPS/////////////
		/////////////////////////
		List<Document> documentsGrups = new ArrayList<Document>();
		for (Grup g : grupsBD) {
			Document doc = new Document
					("cod", g.getCod())
					.append("nom", g.getNom())
					.append("dia", g.getDia())
					.append("pais", g.getPais());
			// afegim llista de objectes artista
			List<Document> artistes = new ArrayList<Document>();
			for (Pertany p : g.getLlistaPertanys()) {
				Document docArtista = new Document
						("dni", p.getArtista().getDni())
						.append("nom", p.getArtista().getNom())
						.append("funcio", p.getFuncio());
				artistes.add(docArtista);
			}
			doc.append("artistes", artistes);
			// afegim llista de objectes club
			List<Document> clubs = new ArrayList<Document>();
			for (Club comp : g.getLlistaClubs()) {
				Document docClub = new Document
						("cod", comp.getCod())
						.append("nom", comp.getNom())
						.append("seu", comp.getSeu())
						.append("num", comp.getNum());
				clubs.add(docClub);
			}
			doc.append("clubs", clubs);
			documentsGrups.add(doc);
		}

		//////////////////////////
		///////COMPANYIES/////////
		/////////////////////////
		List<Document> documentsCompanyies = new ArrayList<Document>();
		for (Companyia comp : companyiesBD) {
			Document docCompanyia = new Document
					("cod", comp.getCod())
					.append("nom", comp.getNom())
					.append("dir", comp.getDir())
					.append("fax", comp.getFax())
					.append("tfno", comp.getTfno());
			// afegim llista de objectes discs
			List<Document> documentsDiscs = new ArrayList<Document>();
			for (Disc disc : comp.getLlistaDiscs()) {
				Document docDisc = new Document
						("cod", disc.getCod())
						.append("nom", disc.getNom())
						.append("dia", disc.getDia())
						.append("cod_gru", disc.getGrup().getCod());
				List<Document> documentsCansons = new ArrayList<Document>();
				for(Esta e : disc.getLlistaCansons()) {
					Document docCanso = new Document
							("cod",e.getCanso().getCod())
							.append("titol", e.getCanso().getTitol())
							.append("duracio", e.getCanso().getDuracio());
					documentsCansons.add(docCanso);
				}
				docDisc.append("cansons", documentsCansons);
				documentsDiscs.add(docDisc);
			}
			docCompanyia.append("discs", documentsDiscs);
			documentsCompanyies.add(docCompanyia);
		}
		
		//INSERTEM EN MONGO
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");

		coleccio.insertMany(documentsGrups);
		System.out.println("GRUPS, ARTISTES I CLUBS INSERTATS");
		
		coleccio = basedades.getCollection("companyies");
		coleccio.insertMany(documentsCompanyies);
		System.out.println("COMPANYIES, DISCS I CANSONS INSERTATS");

		sesion.getTransaction().commit();
	}
}
