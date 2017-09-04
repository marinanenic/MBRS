package generator.fmmodel;

public abstract class FMElement {
	
	private String name;
	private String label;
	//protected List<Stereotype> appliedStereotypes = new ArrayList<Stereotype>();
	protected String appliedStereotype;
	
	public FMElement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean hasName() {
		return name != null;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getAppliedStereotype() {
		return appliedStereotype;
	}
	
	public void setAplliedStereotyoe(String stereotypes) {
		this.appliedStereotype = stereotypes;
	}
}