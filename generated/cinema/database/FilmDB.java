package cinema.database;

import java.util.List;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import cinema.*;

public class FilmDB {
	private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();

	@SuppressWarnings("unchecked")
	public static List<Film> getFilmovi(){
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		List<Film> results = session.createQuery("from Film").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void saveFilm(Film film) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.persist(film);
		t.commit();
		session.close(); 
	}
	
	public static Film getFilmById(int id) {
		Session session=factory.openSession();
		Film film = (Film)session.get(Film.class, id);
		session.close();  
		return film;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Film> searchFilmovi(String naziv) {
	List<Film> results = null;
	Session session=factory.openSession();
	Criteria c = session.createCriteria(Film.class, "film");
	if(naziv != null)
		c.add(Restrictions.like("naziv", "%"+naziv+"%").ignoreCase());
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void updateFilm(Film film) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.merge(film);
		t.commit();
	}
	
	public static void deleteFilm(int id) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		Film result = getFilmById(id);
		session.delete(result);
		t.commit();
	}
	
}
