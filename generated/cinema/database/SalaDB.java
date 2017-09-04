package cinema.database;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import cinema.*;

public class SalaDB {

	@SuppressWarnings("unchecked")
	public static List<Sala> getSale(){
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Sala> results = session.createQuery("from Sala").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void saveSala(Sala sala) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.persist(sala);
		t.commit();
		session.close(); 
	}
	
	public static Sala getSalaById(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Sala sala = (Sala)session.get(Sala.class, id);
		session.close();  
		return sala;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Sala> searchSale(String naziv) {
	List<Sala> results = null;
	Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
	Criteria c = session.createCriteria(Sala.class, "sala");
	if(naziv != null)
		c.add(Restrictions.like("naziv", "%"+naziv+"%").ignoreCase());
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void updateSala(Sala sala) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.merge(sala);
		t.commit();
	}
	
	public static void deleteSala(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Sala result = getSalaById(id);
		session.delete(result);
		t.commit();
	}
	
}
