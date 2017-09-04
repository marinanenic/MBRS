package cinema.database;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import cinema.*;

public class SjedisteDB {

	@SuppressWarnings("unchecked")
	public static List<Sjediste> getSjedista(){
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Sjediste> results = session.createQuery("from Sjediste").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void saveSjediste(Sjediste sjediste) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.persist(sjediste);
		t.commit();
		session.close(); 
	}
	
	public static Sjediste getSjedisteById(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Sjediste sjediste = (Sjediste)session.get(Sjediste.class, id);
		session.close();  
		return sjediste;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Sjediste> searchSjedista(Integer salasala_id, String salanaziv) {
	List<Sjediste> results = null;
	Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
	Criteria c = session.createCriteria(Sjediste.class, "sjediste");
	if(salasala_id != null)
		c.add(Restrictions.eq("sala.sala_id", salasala_id));
	c.createAlias("sjediste.sala", "sala");
	if(salanaziv != null)
		c.add(Restrictions.like("sala.naziv", "%"+salanaziv+"%").ignoreCase());
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void updateSjediste(Sjediste sjediste) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.merge(sjediste);
		t.commit();
	}
	
	public static void deleteSjediste(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Sjediste result = getSjedisteById(id);
		session.delete(result);
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Sjediste> getSjedistaFromSala(int sala_id) {
		List<Sjediste> results = null;
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Criteria c = session.createCriteria(Sjediste.class, "sjediste");
		c.createAlias("sjediste.sala", "sala");
		c.add(Restrictions.eq("sala.sala_id", sala_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
}
