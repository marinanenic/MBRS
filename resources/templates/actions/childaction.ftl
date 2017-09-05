package ${class.typePackage}.actions;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import ${class.typePackage}.database.*;
import ${class.typePackage}.view.MyApp;
import ${class.typePackage}.view.${class.name}Panel;

@SuppressWarnings("serial")
public class ${class.name}ChildAction extends AbstractAction{
	
	<#list properties as p>
	<#if p.classType?? && p.upper == 1>
	<#if p.classType.properties?first.type == "int">
	Integer<#else>${p.classType.properties?first.type?cap_first}</#if> ${p.type?lower_case}_id;
	</#if>
	</#list>
	
	public ${class.name}ChildAction(String name<#list properties as p><#if p.classType?? && p.upper == 1>, <#rt>
<#if p.classType.properties?first.type == "int">Integer <#else>${p.classType.properties?first.type?cap_first} </#if>${p.type?lower_case}_id</#if></#list>) {
		super(name);
		<#list properties as p>
		<#if p.classType?? && p.upper == 1>
		this.${p.type?lower_case}_id = ${p.type?lower_case}_id;
		</#if>
		</#list>
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JDialog ${class.name?lower_case}childdialog = new JDialog();
		Dimension parentSize = MyApp.instance.getSize(); 
		${class.name?lower_case}childdialog.setMinimumSize(new Dimension(2*parentSize.width/3, 2*parentSize.height/3));
		Point p = MyApp.instance.getLocation(); 
		${class.name?lower_case}childdialog.setLocation(p.x + parentSize.width / 6, p.y + parentSize.height / 6);
		${class.name}Panel ${class.name?lower_case}panel = null;
		<#assign first = true>
		<#list properties as p>
		<#if p.classType?? && p.upper == 1>
		<#if !first>else <#else><#assign first= false></#if>if (${p.type?lower_case}_id != null)
			${class.name?lower_case}panel = new ${class.name}Panel(${class.name}DB.get${class.label}From${p.type}(${p.type?lower_case}_id), -1, ${p.type}DB.get${p.type}ById(${p.type?lower_case}_id));
		</#if>
		</#list>
		${class.name?lower_case}childdialog.add(${class.name?lower_case}panel);
		${class.name?lower_case}childdialog.setVisible(true);
	}
	
}
