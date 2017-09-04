package generator.fmmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FMClass extends FMType {	
	
	//Class properties
	private List<FMProperty> FMProperties = new ArrayList<FMProperty>();
	//list of packages (for import declarations) 
	private List<String> importedPackages = new ArrayList<String>();
	private List<String> operations = new ArrayList<String>();
	/** @ToDo: add list of methods */
	
	//polja za StandardForm stereotip
	private Boolean add;
	private Boolean delete;
	private Boolean update;
	private Boolean needZoom= false;
	private Boolean needChild=false;
	private String menu;
	private String classId;
	
	public FMClass(String name, String classPackage) {
		super(name, classPackage);		
		this.add = true;
		this.delete = true;
		this.update = true;
	}
	
	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}	
	
	public List<FMProperty> getProperties(){
		return FMProperties;
	}
	
	public Iterator<FMProperty> getPropertyIterator(){
		return FMProperties.iterator();
	}
	
	public void addProperty(FMProperty property){
		FMProperties.add(property);		
	}
	
	public int getPropertyCount(){
		return FMProperties.size();
	}
	
	public List<String> getImportedPackages(){
		return importedPackages;
	}

	public Iterator<String> getImportedIterator(){
		return importedPackages.iterator();
	}
	
	public void addImportedPackage(String importedPackage){
		importedPackages.add(importedPackage);		
	}
	
	public int getImportedCount(){
		return FMProperties.size();
	}
	
	public Boolean getNeedZoom() {
		return needZoom;
	}

	public void setNeedZoom(Boolean needZoom) {
		this.needZoom = needZoom;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Boolean getNeedChild() {
		return needChild;
	}

	public void setNeedChild(Boolean needChild) {
		this.needChild = needChild;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void addOperation(String operation){
		operations.add(operation);		
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	
	
}
