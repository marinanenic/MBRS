package cinema;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="projekcija")
public class Projekcija {  
	@Id
    private int projekcija_id;
    private Date datum;
    private Date vrijeme;
	@ManyToOne
	@JoinColumn(name="film")
    private Film film;
	@ManyToOne
	@JoinColumn(name="sala")
    private Sala sala;
    private float cijena;
	@Enumerated(EnumType.ORDINAL)
    private TipProjekcije tip;
    @OneToMany(targetEntity=Rezervacija.class, mappedBy="projekcija", fetch=FetchType.EAGER)
    private Set<Rezervacija> rezervacije = new HashSet<Rezervacija>();

	public Projekcija(){}

	public int getProjekcija_id(){
    	return projekcija_id;
    }
      
    public void setProjekcija_id(int projekcija_id){
         this.projekcija_id = projekcija_id;
    }
      
	public Date getDatum(){
    	return datum;
    }
      
    public void setDatum(Date datum){
         this.datum = datum;
    }
      
	public Date getVrijeme(){
    	return vrijeme;
    }
      
    public void setVrijeme(Date vrijeme){
         this.vrijeme = vrijeme;
    }
      
	public Film getFilm(){
    	return film;
    }
      
    public void setFilm(Film film){
         this.film = film;
    }
      
	public Sala getSala(){
    	return sala;
    }
      
    public void setSala(Sala sala){
         this.sala = sala;
    }
      
	public float getCijena(){
    	return cijena;
    }
      
    public void setCijena(float cijena){
         this.cijena = cijena;
    }
      
	public TipProjekcije getTip(){
    	return tip;
    }
      
    public void setTip(TipProjekcije tip){
         this.tip = tip;
    }
      
	public Set<Rezervacija> getRezervacije(){
         return rezervacije;
    }
      
    public void setRezervacije( Set<Rezervacija> rezervacije){
         this.rezervacije = rezervacije;
      }

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(this.projekcija_id);
		return sb.toString();
	}

}
