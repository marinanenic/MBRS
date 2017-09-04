package generator.fmmodel;

import java.util.ArrayList;
import java.util.List;

public class FMModel {
	
	private List<FMClass> classes = new ArrayList<FMClass>();
	private List<FMEnumeration> enumerations = new ArrayList<FMEnumeration>();
	private static FMModel model;
	//....
	/** @ToDo: Add lists of other elements, if needed */
	private FMModel() {
		
	}
	
	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();			
		}
		return model;
	}
	
	public List<FMClass> getClasses() {
		return classes;
	}
	public void setClasses(List<FMClass> classes) {
		this.classes = classes;
	}
	public void addClass(FMClass c) {
		this.classes.add(c);
	}
	public List<FMEnumeration> getEnumerations() {
		return enumerations;
	}
	public void setEnumerations(List<FMEnumeration> enumerations) {
		this.enumerations = enumerations;
	}
	public void addEnumeration(FMEnumeration e) {
		this.enumerations.add(e);
	}
}