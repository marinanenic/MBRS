package generator.fmmodel;

public class FMProperty extends FMElement  {
	private String classId;
	//Property type
	private String type;
	// Property visibility (public, private, protected, package)
	private String visibility;
	//Multiplicity (lower value)
	private Integer lower;
	//Multiplicity (upper value) 
	private Integer upper;
	//Default value
	private String defaultValue;
	private FMClass classType;
	private FMEnumeration enumType;
	
	/** @ToDo: Add length, precision, unique... whatever is needed for ejb class generation
	 * Also, provide these meta-attributes or tags in the modeling languange metaclass or 
	 * stereotype */
	//length, precision, component, searchBy, lookupLabel, depth, searchChild
	private String component;
	private Boolean searchBy = false;
	private String lookupLabel;
	private int depth;
	private Boolean searchChild=false;
	private Boolean id = false;
	
	public FMProperty(String name, String type, String visibility, int lower, int upper, String classId) {
		super(name);
		this.type = type;
		this.visibility = visibility;
		
		this.classId = classId;
		
		this.lower = lower;
		this.upper = upper;		
	}

	public String getClassId() {
		return this.classId;
	}
	
	public void setClassId(String id) {
		this.classId = id;
	}
	
	public String getComponent() {
		return component;
	}

	public Boolean getId() {
		return id;
	}

	public void setId(Boolean id) {
		this.id = id;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Boolean getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(Boolean searchBy) {
		this.searchBy = searchBy;
	}

	public String getLookupLabel() {
		return lookupLabel;
	}

	public void setLookupLabel(String lookupLabel) {
		this.lookupLabel = lookupLabel;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Boolean getSearchChild() {
		return searchChild;
	}

	public void setSearchChild(Boolean searchChild) {
		this.searchChild = searchChild;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String d) {
		this.defaultValue = d;
	}

	public FMClass getClassType() {
		return classType;
	}

	public void setClassType(FMClass classType) {
		this.classType = classType;
	}

	public FMEnumeration getEnumType() {
		return enumType;
	}

	public void setEnumType(FMEnumeration enumType) {
		this.enumType = enumType;
	}
	
}

