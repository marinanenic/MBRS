package cinema.database;

import java.util.List;
import java.util.Date;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import cinema.*;

public class ProjekcijaDB {

	@SuppressWarnings("unchecked")
	public static List<Projekcija> getProjekcije(){
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Projekcija> results = session.createQuery("from Projekcija").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void saveProjekcija(Projekcija projekcija) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.persist(projekcija);
		t.commit();
		session.close(); 
	}
	
	public static Projekcija getProjekcijaById(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Projekcija projekcija = (Projekcija)session.get(Projekcija.class, id);
		session.close();  
		return projekcija;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Projekcija> searchProjekcije(Date datumod, Date datumdo, Integer filmfilm_id, String filmnaziv, Integer salasala_id, String salanaziv, TipProjekcije tip) {
	List<Projekcija> results = null;
	Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
	Criteria c = session.createCriteria(Projekcija.class, "projekcija");
	if(datumod != null)
		c.add(Restrictions.ge("datum", datumod));
	if(datumdo != null)
		c.add(Restrictions.le("datum", datumdo));
	if(filmfilm_id != null)
		c.add(Restrictions.eq("film.film_id", filmfilm_id));
	c.createAlias("projekcija.film", "film");
	if(filmnaziv != null)
		c.add(Restrictions.like("film.naziv", "%"+filmnaziv+"%").ignoreCase());
	if(salasala_id != null)
		c.add(Restrictions.eq("sala.sala_id", salasala_id));
	c.createAlias("projekcija.sala", "sala");
	if(salanaziv != null)
		c.add(Restrictions.like("sala.naziv", "%"+salanaziv+"%").ignoreCase());
	if(tip != null)
		c.add(Restrictions.eq("tip", tip));
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void updateProjekcija(Projekcija projekcija) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		session.merge(projekcija);
		t.commit();
	}
	
	public static void deleteProjekcija(int id) {
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		Projekcija result = getProjekcijaById(id);
		session.delete(result);
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Projekcija> getProjekcijeFromFilm(int film_id) {
		List<Projekcija> results = null;
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Criteria c = session.createCriteria(Projekcija.class, "projekcija");
		c.createAlias("projekcija.film", "film");
		c.add(Restrictions.eq("film.film_id", film_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Projekcija> getProjekcijeFromSala(int sala_id) {
		List<Projekcija> results = null;
		Session session=new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		Criteria c = session.createCriteria(Projekcija.class, "projekcija");
		c.createAlias("projekcija.sala", "sala");
		c.add(Restrictions.eq("sala.sala_id", sala_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
}
