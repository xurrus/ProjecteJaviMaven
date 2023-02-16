package interficies;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import hibernate.UtilesHibernate;
import pojos.Artista;
import pojos.*;

public class ObtindreDades {

	public static void main(String[] args) {
		// V
		List<Artista> artistes = new ArrayList<Artista>();
		List<Canso> cansons= new ArrayList<Canso>();
		List<Club> clubs = new ArrayList<Club>();
		List<Companyia> companyies= new ArrayList<Companyia>();
		List<Disc> discs = new ArrayList<Disc>();
		List<Grup> grups = new ArrayList<Grup>();
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		
		System.out.println("***ARTISTES***");
		Query q = sesion.createQuery("SELECT a FROM Artista a");
		artistes = (List<Artista>) q.list();
		for (Artista a : artistes) {
			System.out.println(a);
		}
		
		System.out.println("***GRUPS***");
		q = sesion.createQuery("SELECT g FROM Grup g");
		grups = (List<Grup>) q.list();
		for (Grup g : grups) {
			System.out.println(g);
		}
		
		System.out.println("***CLUBS***");
		q = sesion.createQuery("SELECT c FROM Club c");
		clubs = (List<Club>) q.list();
		for (Club c : clubs) {
			System.out.println(c);
		}
		
		System.out.println("***COMPAYIES***");
		q = sesion.createQuery("SELECT c FROM Companyia c");
		companyies = (List<Companyia>) q.list();
		for (Companyia c : companyies) {
			System.out.println(c);
		}
		
		System.out.println("***DISCS***");
		q = sesion.createQuery("SELECT d FROM Disc d");
		discs = (List<Disc>) q.list();
		for (Disc d: discs) {
			System.out.println(d);
		}
		
		System.out.println("***CANSONS***");
		q = sesion.createQuery("SELECT c FROM Canso c");
		cansons = (List<Canso>) q.list();
		for (Canso c : cansons) {
			System.out.println(c);
		}
		
//		Artista a = (Artista) sesion.get(Artista.class, "1111111111");
//		System.out.println(a);
		sesion.getTransaction().commit();
	}

}
