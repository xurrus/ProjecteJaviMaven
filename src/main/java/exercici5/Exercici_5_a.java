package exercici5;

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
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;

public class Exercici_5_a {

	@SuppressWarnings({ "unchecked", "unused" })
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase basedades = mongoClient.getDatabase("musicaval");
		MongoCollection<Document> coleccio = basedades.getCollection("grups");
		int id_grup=0,id_companyia=0; //COD DEL GRUP EXISTENT
		String nomGrupT,nomCompanyiaT; //ARREPLGAR DEL SCANNER
		Document companyia; //ARREPLEGAR TOT EL DOCUMENT COMPANYIA AL QUE PERTENECERA EL NOU DISC
		List<Document> novesCansons = new ArrayList<Document>(); //LLISTA DE CANSONS DEL NOU DISC
		Document novaCanso; //DOCUMENT PER A CREAR LA NOVA CANSO
		Integer codCanso;//ATRIBUT DE LA NOVA CANSO
		String titolCanso;//ATRIBUT DE LA NOVA CANSO
		Double duracioCanso;//ATRIBUT DE LA NOVA CANSO
		Document nouDisc; //DOCUMENT PER A CREAR EL NOU DISC
		Integer codDisc;//aTRIBUT NOUT DISC
		String nomDisc, diaDisc; //ATRIBUT NOU DISC
		/*
		 * INSERIX UN DISC I UNA CANÇÓ. LA NOVA CANÇÓ PERTANYERÀ AL NOU DISC. EL
		   DISC SERÀ D'UN GRUP I UNA COMPANYIA JA EXISTENT. ES MOSTRARAN ELS
		   MISSATGES D'ERROR CORRESPONENTS EN ELS SEGÜENTS CASOS:
			• LA COMPANYIA A LA QUE PERTANY EL DISC NO EXISTIX.
			• EL GRUP AL QUE PERTANY EL DISC NO EXISTIX. 
		 */
		System.out.println("*** ANEM A CREAR UN DISC I UNA CANSO ***");
		System.out.println("Introdueix el nom del grup que ha fet el disc: ");
		nomGrupT = teclado.nextLine(); 
		//ARREPLEGUEM GRUP
		List<Document> grups = new ArrayList<Document>();
		coleccio.find(eq("nom", nomGrupT))
		.projection(fields(include("cod"),excludeId()))
		.into(grups);
		//COMPROBEM SI EL GRUP EXISTEIX
		if(grups.size() > 0) {
			//OBTINGUENM EL COD_GRU
			id_grup = grups.get(0).getInteger("cod");
			//ARA ARREPLEGUEM LA COMPANYIA
			System.out.println("Introdueix la companyia a la que pertany el nou disc: ");
			nomCompanyiaT = teclado.nextLine();
			coleccio = basedades.getCollection("companyies");
			List<Document> companyies = new ArrayList<Document>();
			coleccio.find(eq("nom", nomCompanyiaT))
			.projection(fields(include("cod","nom","dir","fax","tfno","discs"),excludeId()))
			.into(companyies);
			//COMPROBEM SI LA COMPANYIA EXISTEIX
			if(companyies.size() > 0) {
				//ARREPLEGUEM TOT EL DOCUMENT COMPANYIA
				companyia = companyies.get(0);
				id_companyia = companyia.getInteger("cod");
				//ARREPLEGUEM TOTES LES CANSONS DE LA BD
				List<Document> allCansonsBD = new ArrayList<Document>();
				coleccio.find()
				.projection(fields(include("discs.cansons.cod"),excludeId()))
				.into(allCansonsBD);
				//DEMANEM INFORMACIO DE LA NOVA CANSO
				System.out.println("*CANSO*");
				System.out.println("-------");
				//ENTREM EN BUCLE DE DEMANAR CODI DE LA NOVA CANSO HASTA QUE SIGA VALID
				while(true) {
					System.out.println("Introdueix el codi de la nova canço:");
					codCanso = teclado.nextInt();
					if(!existeCanso(allCansonsBD,codCanso)) {
						break;
					}else {
						System.out.println("¡YA HI HA UNA CANSO AMB IXE CODI!");
					}
				}
				teclado.nextLine();
				System.out.println("Introdueix el titol de la nova canço:");
				titolCanso = teclado.nextLine();
				System.out.println("Introdueix la duradio de la nova canço:");
				duracioCanso = teclado.nextDouble();
				//CREEM DOCUMENT CANSO
				novaCanso = new Document("cod",codCanso).append("titol", titolCanso).append("duracio", duracioCanso);
				//AFEGIM LA NOVA CANSOA LA LLISTA DE NOVES CANSONS
				novesCansons.add(novaCanso);
				
				//ARREPLEGUEM TOTS ELS DISCS DE LA BD 
				List<Document> allDiscsBD = new ArrayList<Document>();
				coleccio.find()
				.projection(fields(include("discs.cod"),excludeId()))
				.into(allDiscsBD);
				//DEMANEM INFORMACIO DEL NOU DISC
				System.out.println("*DISC*");
				System.out.println("-------");
				//ENTREM EN BUCLE DE DEMANAR CODI DEL NOU DISC HASTA QUE SIGA VALID
				while(true) {
					System.out.println("Introdueix el codi del nou disc:");
					codDisc = teclado.nextInt();
					if(!existeDisc(allDiscsBD,codDisc)) {
						break;
					}else {
						System.out.println("¡YA HI HA UN DISC AMB IXE CODI!");
					}
				}
				teclado.nextLine();
				System.out.println("Introdueix nom del nou disc:");
				nomDisc = teclado.nextLine();
				System.out.println("Introdueix dia de ixida del nou disc (yyyy-mm-dd hora:minut:segon) :");
				diaDisc = teclado.nextLine();
				//CREEM EL NOU DISC
				nouDisc = new Document("cod",codDisc).append("nom", nomDisc).append("dia", diaDisc).append("cod_gru", id_grup).append("cansons", novesCansons);
				
				//ARREPLEGUEM DISCS EXISTENTS DE LA COMPANYIA
				List<Document> discsExistents = new ArrayList<Document>();
				discsExistents = (List<Document>)companyia.get("discs");
				//A LA LLISTA DE DISCS AFEGIM EL DISCS NOU
				discsExistents.add(nouDisc);
				
				UpdateResult modificador = coleccio.updateOne(eq("cod",id_companyia), new Document("$set",new Document("discs",discsExistents)));
				System.out.println("\nDisc afegit a la companyia " + companyia.get("nom"));
			}else {
				//SI LA COMPANYIA NO EXISTEIX
				System.out.println("La companyia "+nomCompanyiaT + " no existeix");
			}
		}else {
			//NO EXISTEIX EL NOM DE GRUP
			System.out.println("El grup "+ nomGrupT+" no existeix");
		}
		teclado.close();
	}
	
	@SuppressWarnings("unchecked")
	private static boolean existeCanso(List<Document> lista, int cod) {
		boolean existe = false;
		for(Document l : lista) {
			List<Document> discs = new ArrayList<Document>();
			discs = (List<Document>)l.get("discs");
			if(discs.size() > 0) {
				for(Document d : discs) {
					List<Document> cansons  = new ArrayList<Document>();
					cansons = (List<Document>) d.get("cansons");
					if(cansons.size() > 0) {
						for(Document c : cansons) {
							if(c.getInteger("cod") == cod) {
								existe = true;
							}
						}
					}
				}
			}
		}
		return existe;
	}
	
	@SuppressWarnings("unchecked")
	private static boolean existeDisc(List<Document> lista, int cod) {
		boolean existe = false;
		for(Document l : lista) {
			List<Document> discs = new ArrayList<Document>();
			discs = (List<Document>)l.get("discs");
			if(discs.size() > 0) {
				for(Document d : discs) {
					if(d.getInteger("cod") == cod) {
						existe = true;
					}
				}
			}
		}
		return existe;
	}
}
