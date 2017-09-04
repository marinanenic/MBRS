package generator.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import generator.fmmodel.FMClass;
import generator.fmmodel.FMEnumeration;
import generator.fmmodel.FMModel;
import generator.fmmodel.FMProperty;
import generator.fmmodel.FMType;


public class Handler extends DefaultHandler {
	String oneup = "";
	FMModel model = FMModel.getInstance();
	List<String> packages = new ArrayList<String>();
	FMType currentElement;
	String currentClassId;
	String currentEnumId;
	FMProperty currentProperty;

	HashMap<String, FMClass> allClasses = new HashMap<String, FMClass>();
	HashMap<String, FMEnumeration> allEnums = new HashMap<String, FMEnumeration>();
	HashMap<FMProperty, String> incompleteProperties = new HashMap<FMProperty, String>();
	HashMap<String, FMProperty> allProperties = new HashMap<String, FMProperty>();
	
	@SuppressWarnings("unused")
	@Override
	public void startElement(String namespaceURI, String sName, String qName, Attributes attrs) throws SAXException {
		if("".equals(sName))
			sName = qName;
		String xmiType, name, xmiId, type, visibility;
		String label, length, precision, component, lookupLabel, base;
		int depth;
		Boolean searchBy, searchChild, id;
		switch(sName) {
		case "uml:Model":
			xmiType = getAttributeValue("xmi:type", attrs);
			name = getAttributeValue("name", attrs);
			if (xmiType != null) {
				System.out.println("Model name "+name);
				oneup = "model";
			}
			else oneup = "";
			break;
			
		case "packagedElement":
			xmiType = getAttributeValue("xmi:type", attrs);
			xmiId = getAttributeValue("xmi:id", attrs);
			name = getAttributeValue("name", attrs);
			visibility = getAttributeValue("visibility", attrs);
			if (xmiType != null && !xmiType.equals("uml:Association")) {
				if (xmiType.endsWith("Package")) {// paket u paketu => spoji imena tackom
					packages.add(name.toLowerCase());
				}
					
				else { 
					String packageName = "";
					for (String p : packages) {
						packageName += p;
					}
					if(xmiType.endsWith("Class") && currentElement == null){
						currentElement = new FMClass(name, packageName);
						currentClassId = xmiId;
						((FMClass)currentElement).setClassId(xmiId);
						//allClasses.put(xmiId, (FMClass)currentElement);
					}
					else if(xmiType.endsWith("Enumeration") && currentElement == null) {
						currentElement = new FMEnumeration(name, packageName);
						currentEnumId = xmiId;
						//allEnums.put(xmiId, (FMEnumeration)currentElement);
					}
					
					for(Iterator<Entry<FMProperty, String>> it = incompleteProperties.entrySet().iterator(); it.hasNext(); ) {
						Entry<FMProperty, String> entry = it.next();
					    //Prodji kroz sve propertyje koji nemaju dodjeljen tip i dodijeli naziv trenutne klase/enumeracije kao tip 
						//ako se id-evi poklapaju
						//Kad dodjelis odgovarajuci tip smjesti property u klasu kojoj pripada (id klase je dat kao value)
					    if(entry.getKey().getType().equals(xmiId)) {
					    	entry.getKey().setType(name);
					    	if (currentClassId != null)
					    		entry.getKey().setClassType((FMClass)currentElement);
					    	else
					    		entry.getKey().setEnumType((FMEnumeration)currentElement);
					    	allClasses.get(entry.getValue()).addProperty(entry.getKey());
					    	it.remove();
					    }
					}
				}
				
				System.out.println("\nElement type "+xmiType.split(":")[1]);
				System.out.println(xmiType.split(":")[1]+" name: "+name);
			}
			else 
				currentClassId = xmiId;
			break;
			
		case "ownedAttribute":
			if (oneup.equals("model")) {
				xmiId = getAttributeValue("xmi:id", attrs);
				name = getAttributeValue("name", attrs);
				type = getAttributeValue("type", attrs);
				visibility = getAttributeValue("visibility", attrs);
				
				if (currentProperty == null && currentClassId != null) {
					currentProperty = new FMProperty(name, type, visibility, 1, 1, currentClassId);
					allProperties.put(xmiId, currentProperty);
					if (type != null) {
						if(allClasses.get(type) != null){
							currentProperty.setType(allClasses.get(type).getName());	
							currentProperty.setClassType(allClasses.get(type));
						}
						else if (allEnums.get(type) != null) {
							currentProperty.setType(allEnums.get(type).getName());	
							currentProperty.setEnumType(allEnums.get(type));
						}
						else
							incompleteProperties.put(currentProperty, currentClassId); 
					}
				}
				System.out.println("\nProperty name "+name);
			}
			break;
			
		case "ownedOperation":
			if (oneup.equals("model")) {
				xmiId = getAttributeValue("xmi:id", attrs);
				name = getAttributeValue("name", attrs);
				visibility = getAttributeValue("visibility", attrs);
				
				if (currentClassId != null) {
					((FMClass)currentElement).addOperation(name);
				}
				System.out.println("\nOperation name "+name);
			}
			break;
			
		case "referenceExtension":
			if (oneup.equals("model")) {
				String referentPath = getAttributeValue("referentPath", attrs);
				type = referentPath.split("::")[3];
				if (type.toLowerCase().contains("date")) {
					type = "Date";
					if (!checkPackage(((FMClass)currentElement).getImportedPackages(), "java.util.Date"))
							((FMClass)currentElement).addImportedPackage("java.util.Date");
				}
				currentProperty.setType(type);
			}
			break;
			
		case "lowerValue":
			if (oneup.equals("model")) {
				String value = getAttributeValue("value", attrs);
				if (value == null)
					value = "0";
				int v = Integer.parseInt(value);
				currentProperty.setLower(v);
				System.out.println("lowerValue = "+value+ " as a number = "+v);
			}
			break;
			
		case "upperValue":
			if (oneup.equals("model")) {
				String value = getAttributeValue("value", attrs);
				if (value.equals("*")){
					value = "-1";
					if (!checkPackage(((FMClass)currentElement).getImportedPackages(), "java.util.Set")) {
						((FMClass)currentElement).addImportedPackage("java.util.HashSet");
						((FMClass)currentElement).addImportedPackage("java.util.Set");
					}
				}
				int v = Integer.parseInt(value);
				currentProperty.setUpper(v);
				System.out.println("upperValue = "+value+ " as a number = "+v);
			}
			break;
			
		case "defaultValue":
			if (oneup.equals("model")) {
				xmiType = getAttributeValue("xmi:type", attrs);
				String value = getAttributeValue("value", attrs);
				if (value == null) 
					if (xmiType.equals("uml:LiteralBoolean"))
						value = "false";
					else if (xmiType.equals("uml:InstanceValue")) 
						value = getAttributeValue("instance", attrs);
					else
						value = "0";
				currentProperty.setDefaultValue(value);
				System.out.println("defaultValue = "+value);
			}
			break;
			
		case "ownedLiteral":
			/** @ToDo: odradi i za zamijenu default values kod propertyja kad su dati preko literal id-ja */
			if (oneup.equals("model")) {
				xmiId = getAttributeValue("xmi:id", attrs);
				name = getAttributeValue("name", attrs);
			
			    ((FMEnumeration)currentElement).addValue(name);
				System.out.println("Enumeration literal "+name);
			}
			break;
			
		case "UIProfile:UIProperty":
			//length, precision, component, searchBy, label
			label = getAttributeValue("label", attrs);
			length = getAttributeValue("length", attrs);
			precision = getAttributeValue("precision", attrs);
			component = getAttributeValue("component", attrs);
			if (getAttributeValue("searchBy", attrs) != null)
				searchBy = true;
			else
				searchBy = false;
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("UIProperty");
					p.setComponent(component);
					p.setLabel(label);
					//p.setDepth(depth!=null?Integer.parseInt(depth):1);
					p.setSearchBy(searchBy);
					System.out.println("Primjenjen stereotip UIProperty na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;

		case "UIProfile:ReadOnly":
			//length, precision, component, searchBy
			label = getAttributeValue("label", attrs);
			length = getAttributeValue("length", attrs);
			precision = getAttributeValue("precision", attrs);
			component = getAttributeValue("component", attrs);
			if (getAttributeValue("id", attrs) != null)
				id = true;
			else
				id = false;
			if (getAttributeValue("searchBy", attrs) != null)
				searchBy = true;
			else
				searchBy = false;
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("ReadOnly");
					p.setComponent(component);
					p.setLabel(label);
					p.setSearchBy(searchBy);
					p.setId(id);
					System.out.println("Primjenjen stereotip ReadOnly na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;
			
		case "UIProfile:UIElement":
			label = getAttributeValue("label", attrs);
			base = getAttributeValue("base_Element", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("UIElement");
					p.setLabel(label);
					System.out.println("Primjenjen stereotip UIElement na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;
			
		case "UIProfile:Lookup":
			//length, precision, component, searchBy, lookupLabel, depth, searchChild
			label = getAttributeValue("label", attrs);
			length = getAttributeValue("length", attrs);
			precision = getAttributeValue("precision", attrs);
			component = getAttributeValue("component", attrs);
			lookupLabel = getAttributeValue("lookupLabel", attrs);
			if (getAttributeValue("depth", attrs) != null)
				depth = Integer.parseInt(getAttributeValue("depth", attrs));
			else
				depth = 1;
			if (getAttributeValue("searchBy", attrs) != null)
				searchBy = true;
			else
				searchBy = false;
			if (getAttributeValue("searchChild", attrs) != null)
				searchChild = true;
			else
				searchChild = false;
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("Lookup");
					p.setComponent(component);
					p.setDepth(depth);
					p.setLabel(label);
					p.setSearchBy(searchBy);
					p.setSearchChild(searchChild);
					p.setLookupLabel(lookupLabel);
					System.out.println("Primjenjen stereotip Lookup na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;
			
		case "UIProfile:Editable":
			//length, precision, component, searchBy
			label = getAttributeValue("label", attrs);
			length = getAttributeValue("length", attrs);
			precision = getAttributeValue("precision", attrs);
			component = getAttributeValue("component", attrs);
			if (getAttributeValue("searchBy", attrs) != null)
				searchBy = true;
			else
				searchBy = false;
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("Editable");
					p.setComponent(component);
					p.setLabel(label);
					p.setSearchBy(searchBy);
					System.out.println("Primjenjen stereotip Editable na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;
		
		case "UIProfile:Zoom":
			label = getAttributeValue("label", attrs);
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("Zoom");
					p.setLabel(label);
					allClasses.get(currentProperty.getClassType().getClassId()).setNeedZoom(true);
					System.out.println("Primjenjen stereotip Zoom na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					System.out.println("hhh"+currentProperty.getClassType().getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;
		
		case "UIProfile:Child":
			label = getAttributeValue("label", attrs);
			base = getAttributeValue("base_Property", attrs);
			currentProperty = allProperties.get(base);
			for (FMProperty p: allClasses.get(currentProperty.getClassId()).getProperties()){
				if (currentProperty.getName().equals(p.getName())){
					p.setAplliedStereotyoe("Child");
					p.setLabel(label);
					allClasses.get(currentProperty.getClassType().getClassId()).setNeedChild(true);
					System.out.println("Primjenjen stereotip Child na property "+p.getName()
					+" klase "+allClasses.get(currentProperty.getClassId()).getName());
					if(label != null)
						System.out.println("Label name "+label);
					break;
				}
			}
			currentProperty = null;
			break;

		case "UIProfile:StandardForm":
			//add, delete, update, menu
			String menu = getAttributeValue("menu", attrs);
			label = getAttributeValue("label", attrs);
			base = getAttributeValue("base_Class", attrs);
			allClasses.get(base).setAplliedStereotyoe("StandardForm");
			allClasses.get(base).setMenu(menu);
			if (getAttributeValue("add", attrs) == null)
				allClasses.get(base).setAdd(false);
			if (getAttributeValue("delete", attrs) == null)
				allClasses.get(base).setDelete(false);
			if (getAttributeValue("update", attrs) == null)
				allClasses.get(base).setUpdate(false);
			allClasses.get(base).setLabel(label);
			System.out.println("Primjenjen stereotip StandardForm na klasu "+allClasses.get(base).getName());
			if(label != null)
				System.out.println("Label name "+label);
			break;
		
		default:
			break;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
		if ("".equals(sName))
			sName = qName;
		switch(sName) {
		case "uml:Model":
			if (oneup.equals("model")) {
				System.out.println("</"+sName+">");
				oneup = "";
				break;
			}
			
		case "packagedElement":
			if (currentElement != null) { 
				if (currentClassId != null) { //u pitanju je klasa
					allClasses.put(currentClassId, (FMClass)currentElement);
					currentClassId = null;
					System.out.println("kraj klase "+currentElement.getName()+"\n");
				}
				else { //u pitanju je enumeracija
					allEnums.put(currentEnumId, (FMEnumeration)currentElement);
					currentEnumId= null;
					System.out.println("kraj enuma "+currentElement.getName());
				}
				currentElement = null;
			} 
			else if (!packages.isEmpty() && currentClassId == null){//u pitanju je paket 
				System.out.println("kraj paketa "+packages.get(packages.size()-1));
				packages.remove(packages.size()-1);
			}
			else if (currentClassId != null)
				currentClassId = null;
			break;
			
		case "ownedAttribute":
			if (currentProperty != null && currentElement != null) {
				//ako nije u nedovrsenim atributima moze se dodati klasi
				if (currentElement instanceof FMClass){
					if (incompleteProperties.get(currentProperty) == null) 
						((FMClass)currentElement).addProperty(currentProperty);
					System.out.println("tip je "+currentProperty.getType());
					System.out.println("kraj propertija " + currentProperty.getName()+"\n");
					currentProperty = null;
				}
			}
			break;
		
		default:
			break;
		}
	}
	
	@Override
	public void endDocument() throws SAXException {
		model.setClasses(new ArrayList<FMClass>(allClasses.values()));
		model.setEnumerations(new ArrayList<FMEnumeration>(allEnums.values()));
	}
	
	/*private String buffer;
	
	@Override
	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		if (buffer == null) 
			buffer = s;
		else
			buffer+=s;
	}*/
	
	private String getAttributeValue(String attrName, Attributes attrs) {
		String retVal = null;
		for (int i = 0; i < attrs.getLength(); i++) {
			String aName = attrs.getLocalName(i);
			if ("".equals(aName))
				aName = attrs.getQName(i);
			if (aName.equals(attrName)) {
				retVal = attrs.getValue(i);
				break;
			}
		}
		return retVal;
	}
	
	private Boolean checkPackage(List<String> packages, String p) {
		for (String pa : packages)
			if (pa.equals(p)) 
				return true;
		return false;
			
	}
}
