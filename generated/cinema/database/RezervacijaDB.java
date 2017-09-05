package cinema.database;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import cinema.*;

public class RezervacijaDB {
	private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();

	@SuppressWarnings("unchecked")
	public static List<Rezervacija> getRezervacije(){
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		List<Rezervacija> results = session.createQuery("from Rezervacija").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void saveRezervacija(Rezervacija rezervacija) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.persist(rezervacija);
		t.commit();
		session.close(); 
	}
	
	public static Rezervacija getRezervacijaById(int id) {
		Session session=factory.openSession();
		Rezervacija rezervacija = (Rezervacija)session.get(Rezervacija.class, id);
		session.close();  
		return rezervacija;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Rezervacija> searchRezervacije(Boolean placena, Integer projekcijaprojekcija_id, Integer sjedistesjediste_id) {
	List<Rezervacija> results = null;
	Session session=factory.openSession();
	Criteria c = session.createCriteria(Rezervacija.class, "rezervacija");
	if(placena != null)
		c.add(Restrictions.eq("placena", placena));
	if(projekcijaprojekcija_id != null)
		c.add(Restrictions.eq("projekcija.projekcija_id", projekcijaprojekcija_id));
	if(sjedistesjediste_id != null)
		c.add(Restrictions.eq("sjediste.sjediste_id", sjedistesjediste_id));
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void updateRezervacija(Rezervacija rezervacija) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.merge(rezervacija);
		t.commit();
	}
	
	public static void deleteRezervacija(int id) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		Rezervacija result = getRezervacijaById(id);
		session.delete(result);
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Rezervacija> getRezervacijeFromProjekcija(int projekcija_id) {
		List<Rezervacija> results = null;
		Session session=factory.openSession();
		Criteria c = session.createCriteria(Rezervacija.class, "rezervacija");
		c.createAlias("rezervacija.projekcija", "projekcija");
		c.add(Restrictions.eq("projekcija.projekcija_id", projekcija_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Rezervacija> getRezervacijeFromSjediste(int sjediste_id) {
		List<Rezervacija> results = null;
		Session session=factory.openSession();
		Criteria c = session.createCriteria(Rezervacija.class, "rezervacija");
		c.createAlias("rezervacija.sjediste", "sjediste");
		c.add(Restrictions.eq("sjediste.sjediste_id", sjediste_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
}
