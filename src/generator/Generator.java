package generator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import freemarker.template.TemplateException;
import generator.fmmodel.FMClass;
import generator.fmmodel.FMEnumeration;
import generator.fmmodel.FMModel;
import generator.fmmodel.FMProperty;

public class Generator extends BasicGenerator {
	private List<String> templates = new ArrayList<String>();
	private List<String> fileNames = new ArrayList<String>();
	private List<String> packages = new ArrayList<String>();

	private List<Boolean> hasSearch = new ArrayList<Boolean>();
	private List<List<String>> actionsToImport = new ArrayList<List<String>>();

	public Generator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
		// TODO Auto-generated constructor stub
	}
	
	public void generate() {
		List<FMClass> classes = FMModel.getInstance().getClasses();
		List<FMEnumeration> enumerations = FMModel.getInstance().getEnumerations();
		//HashMap<String, String> templates = new HashMap<String, String>();
		
		setTemplateName("enumeration");
		setTemplateDir("resources/templates/model");
		for (FMEnumeration en : enumerations) {
			try {
				super.generate();
			} catch (IOException e) {		
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			Writer out;
			Map<String, Object> context = new HashMap<String, Object>();
			try {
				out = getWriter(en.getName(), en.getTypePackage());
				if (out != null) {
					context.clear();
					context.put("class", en);
					context.put("properties", en.getValues());
					getTemplate().process(context, out);
					out.flush();
				}
			} catch (TemplateException e) {	
				JOptionPane.showMessageDialog(null, e.getMessage());
			}	
			catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}		
		}
		
		//go through classes and create import lists
		templates.addAll(Arrays.asList("class", "classdb", "deleteaction", "zoomaction", "classaction", "childaction", "classpanel",
				"classdialog", "zoomdialog"));
		fileNames.addAll(Arrays.asList("", "DB", "DeleteAction", "ZoomAction", "Action", "ChildAction", "Panel", "Dialog", "ZoomDialog"));
		packages.addAll(Arrays.asList("", "database", "actions", "actions", "actions", "actions", "view", "view", "view"));
		
		for (FMClass c : classes) {
			Boolean search = false;
			List<String> importActions = new ArrayList<String>();
			for(FMProperty p : c.getProperties()){
				if(p.getSearchBy())
					search = true;
				if (p.getClassType() != null) {
					if("Child".equals(p.getAppliedStereotype())){
						if (!importActions.contains(p.getType()))
							importActions.add(p.getType());
					}
					else
						for(FMProperty pr: p.getClassType().getProperties()){
							if (pr.getSearchChild())
								search = true;
						}
				}
			}
			hasSearch.add(search);
			actionsToImport.add(importActions);
		}
		
		for(int i = 0; i < templates.size(); i++) {
			setTemplateName(templates.get(i));
			if(!packages.get(i).equals("")) {
				setTemplateDir("resources/templates/"+packages.get(i));
			}
			for (int j = 0; j < classes.size(); j++){
				if(templates.get(i).equals("zoomaction") || templates.get(i).equals("zoomdialog")){
					if(classes.get(j).getNeedZoom())
						generateClassFile(classes.get(j),i, j);
				}
				else if(templates.get(i).equals("childaction")){
					if(classes.get(j).getNeedChild())
						generateClassFile(classes.get(j),i, j);
				}
				else 
					generateClassFile(classes.get(j),i, j);
				
			}
		
		}
		
		templates.clear();
		fileNames.clear();
		packages.clear();
		templates.add("myapp");
		templates.add("mainframe");
		templates.add("noneditabletableModel");
		templates.add("addeditaction");
		fileNames.add("MyApp");
		fileNames.add("MainFrame");
		fileNames.add("NonEditableTableModel");
		fileNames.add("AddEditAction");
		packages.add("view");
		packages.add("view");
		packages.add("view");
		packages.add("actions");
		for (int i = 0; i<templates.size(); i++) {
			setTemplateName(templates.get(i));
			setTemplateDir("resources/templates/"+packages.get(i));
			try {
				super.generate();
			} catch (IOException e) {		
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			Writer out;
			Map<String, Object> context = new HashMap<String, Object>();
			try {
				out = getWriter(fileNames.get(i), getFilePackage()+"."+packages.get(i));
				setFilePackage(getFilePackage().split("."+packages.get(i))[0]);
				if (out != null) {
					context.clear();
					context.put("classes", classes);
					context.put("enumerations", enumerations);
					context.put("root", getFilePackage());
					context.put("package", packages.get(i));
					getTemplate().process(context, out);
					out.flush();
					
				}
			} catch (TemplateException e) {	
				JOptionPane.showMessageDialog(null, e.getMessage());
			}	
			catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}	
		}
		
		setOutputFileName("{0}.xml");
		setTemplateName("hibernate");
		setTemplateDir("resources/templates/");
		try {
			super.generate();
		} catch (IOException e) {		
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter("hibernate.cfg", "");
			if (out != null) {
				context.clear();
				context.put("classes", classes);
				getTemplate().process(context, out);
				out.flush();
				
			}
		} catch (TemplateException e) {	
			JOptionPane.showMessageDialog(null, e.getMessage());
		}	
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}	
	}
	
	private void generateClassFile(FMClass c, int i, int j) {
		try {
			super.generate();
		} catch (IOException e) {		
			
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		Writer out;
		Map<String, Object> context = new HashMap<String, Object>();
		try {
			out = getWriter(c.getName()+fileNames.get(i), c.getTypePackage()+"."+packages.get(i));
			if(!packages.get(i).equals("")) {
				setFilePackage(getFilePackage().split("."+packages.get(i))[0]);
			}
			if (out != null) {
				context.clear();
				context.put("class", c);
				context.put("properties", c.getProperties());
				context.put("package", packages.get(i));
				context.put("search", hasSearch.get(j));
				context.put("importActions", actionsToImport.get(j));
				getTemplate().process(context, out);
				out.flush();
			}
		} catch (TemplateException e) {	
			JOptionPane.showMessageDialog(null, e.getMessage());
		}	
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}


