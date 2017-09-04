package cinema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="sala")
public class Sala {  
	@Id
    private int sala_id;
    private String naziv;
    private String opis;
    private Integer brojMjesta;
    private Boolean trid;
    private Boolean cetrid;
    @OneToMany(targetEntity=Projekcija.class, mappedBy="sala", fetch=FetchType.EAGER)
    private Set<Projekcija> projekcije = new HashSet<Projekcija>();
    @OneToMany(targetEntity=Sjediste.class, mappedBy="sala", fetch=FetchType.EAGER)
    private Set<Sjediste> sjedista = new HashSet<Sjediste>();

	public Sala(){}

	public int getSala_id(){
    	return sala_id;
    }
      
    public void setSala_id(int sala_id){
         this.sala_id = sala_id;
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
      
	public Integer getBrojMjesta(){
    	return brojMjesta;
    }
      
    public void setBrojMjesta(Integer brojMjesta){
         this.brojMjesta = brojMjesta;
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
	public Set<Sjediste> getSjedista(){
         return sjedista;
    }
      
    public void setSjedista( Set<Sjediste> sjedista){
         this.sjedista = sjedista;
      }

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		sb.append(this.sala_id);
		return sb.toString();
	}

}
