package cinema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="sjediste")
public class Sjediste {  
	@Id
    private int sjediste_id;
    private Integer broj;
    private Integer red;
	@ManyToOne
	@JoinColumn(name="sala")
    private Sala sala;
    @OneToMany(targetEntity=Rezervacija.class, mappedBy="sjediste", fetch=FetchType.EAGER)
    private Set<Rezervacija> rezervacije = new HashSet<Rezervacija>();

	public Sjediste(){}

	public int getSjediste_id(){
    	return sjediste_id;
    }
      
    public void setSjediste_id(int sjediste_id){
         this.sjediste_id = sjediste_id;
    }
      
	public Integer getBroj(){
    	return broj;
    }
      
    public void setBroj(Integer broj){
         this.broj = broj;
    }
      
	public Integer getRed(){
    	return red;
    }
      
    public void setRed(Integer red){
         this.red = red;
    }
      
	public Sala getSala(){
    	return sala;
    }
      
    public void setSala(Sala sala){
         this.sala = sala;
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
		sb.append(this.sjediste_id);
		return sb.toString();
	}

}
