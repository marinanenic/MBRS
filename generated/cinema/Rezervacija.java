package cinema;

import javax.persistence.*;

@Entity
@Table(name="rezervacija")
public class Rezervacija {  
	@Id
    private int rezervacija_id;
    private Boolean placena;
	@ManyToOne
	@JoinColumn(name="projekcija")
    private Projekcija projekcija;
	@ManyToOne
	@JoinColumn(name="sjediste")
    private Sjediste sjediste;

	public Rezervacija(){}

	public int getRezervacija_id(){
    	return rezervacija_id;
    }
      
    public void setRezervacija_id(int rezervacija_id){
         this.rezervacija_id = rezervacija_id;
    }
      
	public Boolean getPlacena(){
    	return placena;
    }
      
    public void setPlacena(Boolean placena){
         this.placena = placena;
    }
      
	public Projekcija getProjekcija(){
    	return projekcija;
    }
      
    public void setProjekcija(Projekcija projekcija){
         this.projekcija = projekcija;
    }
      
	public Sjediste getSjediste(){
    	return sjediste;
    }
      
    public void setSjediste(Sjediste sjediste){
         this.sjediste = sjediste;
    }
      

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(this.rezervacija_id);
		return sb.toString();
	}

}
