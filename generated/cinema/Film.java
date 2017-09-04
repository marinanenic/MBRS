package cinema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="film")
public class Film {  
	@Id
    private int film_id;
    private String naziv;
    private String opis;
    private Integer trajanje;
    private Boolean trid;
    private Boolean cetrid;
    @OneToMany(targetEntity=Projekcija.class, mappedBy="film", fetch=FetchType.EAGER)
    private Set<Projekcija> projekcije = new HashSet<Projekcija>();

	public Film(){}

	public int getFilm_id(){
    	return film_id;
    }
      
    public void setFilm_id(int film_id){
         this.film_id = film_id;
    }
      
	public String getNaziv(){
    	return naziv;
    }
      
    public void setNaziv(String naziv){
         this.naziv = naziv;
    }
      
	public String getOpis(){
    	return opis;
    }
      
    public void setOpis(String opis){
         this.opis = opis;
    }
      
	public Integer getTrajanje(){
    	return trajanje;
    }
      
    public void setTrajanje(Integer trajanje){
         this.trajanje = trajanje;
    }
      
	public Boolean getTrid(){
    	return trid;
    }
      
    public void setTrid(Boolean trid){
         this.trid = trid;
    }
      
	public Boolean getCetrid(){
    	return cetrid;
    }
      
    public void setCetrid(Boolean cetrid){
         this.cetrid = cetrid;
    }
      
	public Set<Projekcija> getProjekcije(){
         return projekcije;
    }
      
    public void setProjekcije( Set<Projekcija> projekcije){
         this.projekcije = projekcije;
      }

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(this.film_id);
		return sb.toString();
	}

}
