package ${class.typePackage}.${package};

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ${class.typePackage}.database.${class.name}DB;
import ${class.typePackage}.${class.name};
<#assign date = false>
<#assign radio = false>
<#list properties as p>
<#if p.type == "Date" && !date>
import java.util.Date;
import com.github.lgooddatepicker.components.*;
import java.time.*;
<#assign date = true>
<#elseif p.component?? && p.component == "radioButton" && !radio>
import javax.swing.border.TitledBorder;
<#assign radio = true>
</#if>
<#if p.enumType??>
import ${class.typePackage}.${p.type};
<#elseif p.classType?? && p.upper == 1>
import ${p.classType.typePackage}.database.${p.type}DB;
<#if  p.appliedStereotype?? && p.appliedStereotype == "Zoom">
import ${p.classType.typePackage}.actions.${p.type}ZoomAction;
import ${p.classType.typePackage}.${p.type};
<#else>
<#if p.classType.properties?first.type == "Date">
import java.text.SimpleDateFormat;
</#if>
</#if>
</#if>
</#list>

@SuppressWarnings("serial")
public class ${class.name}Dialog extends JDialog implements ActionListener {
	private ${class.name} ${class.name?lower_case} = null;
	
	public ${class.name}Dialog(){};
	
	public ${class.name}Dialog(Object id, ${properties?first.type} index, Object parent, JPanel panel) {
		super(MainFrame.getInstance(), "Dodaj (${class.name})", true);
		Dimension parentSize = MainFrame.getInstance().getSize(); 
		setMinimumSize(new Dimension(parentSize.width/4, getPreferredSize().height));
		Point p = MainFrame.getInstance().getLocation(); 
		setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		setResizable(false);
		
		JPanel messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		messagePane.setBorder(new EmptyBorder(20, 50, 20, 50));
		
		<#list properties as p>
		<#if p.upper == 1>
		<#if (p.component?? && p.component == "checkbox") || p.type == "Boolean">
		JCheckBox field${p.name} = new JCheckBox("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>");
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(field${p.name});
		
		<#elseif p.component?? && p.component == "radioButton" && p.enumType??>
		JPanel panel${p.name} = new JPanel();
		panel${p.name}.setLayout(new BoxLayout(panel${p.name}, BoxLayout.Y_AXIS));
		panel${p.name}.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>", TitledBorder.LEFT, TitledBorder.TOP));
		ButtonGroup group${p.name} = new ButtonGroup();
		<#list p.enumType.values as value>
		JRadioButton field${value} = new JRadioButton(${p.type}.${value}.toString()<#if value == p.enumType.values?first>, true</#if>);
		group${p.name}.add(field${value});
		panel${p.name}.add(field${value});
		</#list>
		panel${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel${p.name}.getPreferredSize().height));
		messagePane.add(panel${p.name});
		
		<#elseif p.type == "Date">
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		<#if p.component?? && p.component == "dateChooser">
		DatePicker field${p.name} = new DatePicker();
		<#elseif p.component?? && p.component == "timePicker">
		TimePicker field${p.name} = new TimePicker();
		<#else>
		DateTimePicker field${p.name} = new DateTimePicker();
		</#if>
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		field${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}.getPreferredSize().height));
		messagePane.add(label${p.name});
		messagePane.add(field${p.name});
		
		<#elseif p.classType?? && p.appliedStereotype?? && p.appliedStereotype == "Zoom">
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		JPanel panel${p.name} = new JPanel();
		panel${p.name}.setLayout(new BoxLayout(panel${p.name}, BoxLayout.X_AXIS));
		JTextField field${p.name} = new JTextField();
		field${p.name}.setEditable(false);
		JButton button${p.name} = new JButton(new ${p.type}ZoomAction("...", field${p.name}));
		panel${p.name}.add(field${p.name});
		panel${p.name}.add(button${p.name});
		panel${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		if(parent!= null && parent.getClass().equals(${p.type}.class)) {
			field${p.name}.setText(((${p.type})parent).toString());
			button${p.name}.setEnabled(false);
		}
		messagePane.add(label${p.name});
		messagePane.add(panel${p.name});
		
		<#elseif p.classType??>
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		if(MainFrame.${p.classType.label?lower_case}.isEmpty())
			MainFrame.${p.classType.label?lower_case}.addAll(${p.type}DB.get${p.classType.label}());
		String[] list${p.name} = new String[MainFrame.${p.classType.label?lower_case}.size()];
		for(int i = 0; i<MainFrame.${p.classType.label?lower_case}.size(); i++)
			list${p.name}[i] = MainFrame.${p.classType.label?lower_case}.get(i).toString();
		JComboBox<String> field${p.name} = new JComboBox<String>(list${p.name});
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(Box.createRigidArea(new Dimension(0, 5)));
		messagePane.add(label${p.name});
		messagePane.add(field${p.name});
		messagePane.add(Box.createRigidArea(new Dimension(0, 5)));
		
		<#elseif p.enumType??>
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		String[] list${p.name} = new String[${p.type}.values().length];
		for(int i = 0; i<${p.type}.values().length; i++)
			list${p.name}[i] = ${p.type}.values()[i].toString();
		JComboBox<String> field${p.name} = new JComboBox<String>(list${p.name});
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(Box.createRigidArea(new Dimension(0, 5)));
		messagePane.add(label${p.name});
		messagePane.add(field${p.name});
		messagePane.add(Box.createRigidArea(new Dimension(0, 5)));
		
		<#else>
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		<#if p.component?? && p.component == "editor">
		JTextArea field${p.name} = new JTextArea();
		field${p.name}.setRows(10);
		field${p.name}.setLineWrap(true);
		<#else>
		JTextField field${p.name} = new JTextField();
		field${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}.getPreferredSize().height));
		</#if>
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		messagePane.add(label${p.name});
		messagePane.add(field${p.name});
		
		</#if>
		messagePane.add(Box.createRigidArea(new Dimension(0,10)));
		</#if>
		</#list>
		
		if (index != -1) {
			setTitle("Izmijeni (${class.name})");
			this.${class.name?lower_case} = ${class.name}DB.get${class.name}ById((${properties?first.type})id);
			<#list properties as p>
			<#if p.upper == 1>
			<#if p.type == "Boolean">
			field${p.name}.setSelected(this.${class.name?lower_case}.get${p.name?cap_first}());
			<#elseif p.type == "Date">
			<#if p.component?? && p.component == "dateChooser">
			field${p.name}.setDate(this.${class.name?lower_case}.get${p.name?cap_first}().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			<#elseif p.component?? && p.component == "timePicker">
			field${p.name}.setTime(this.${class.name?lower_case}.get${p.name?cap_first}().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
			<#else>
			field${p.name}.setDateTimeStrict(LocalDateTime.ofInstant(this.${class.name?lower_case}.get${p.name?cap_first}().toInstant(), ZoneId.systemDefault()));
			</#if>
			<#elseif p.type == "String">
			field${p.name}.setText(this.${class.name?lower_case}.get${p.name?cap_first}());
			<#elseif p.type =="int" || p.type=="Integer">
			field${p.name}.setText(Integer.toString(this.${class.name?lower_case}.get${p.name?cap_first}()));
			<#else>
			<#if p.classType??>
			<#if p.appliedStereotype?? && p.appliedStereotype == "Zoom">
			field${p.name}.setText(this.${class.name?lower_case}.get${p.name?cap_first}().toString());
			if(parent!= null && parent.getClass().equals(${p.type}.class)) 
				button${p.name}.setEnabled(false);
			<#else>
			field${p.name}.setSelectedItem(this.${class.name?lower_case}.get${p.name?cap_first}().toString());
			</#if>
			<#elseif p.enumType??>
			<#if p.component?? && p.component == "radioButton">
			<#list p.enumType.values as value>
			<#if p.enumType.values?first != value>else </#if>if(this.${class.name?lower_case}.get${p.name?cap_first}().equals(${p.type}.${value}))
				field${value}.setSelected(true);
			</#list>
			<#else>
			field${p.name}.setSelectedItem(this.${class.name?lower_case}.get${p.name?cap_first}());
			</#if>
			<#else>
			field${p.name}.setText(${p.type?cap_first}.toString(this.${class.name?lower_case}.get${p.name?cap_first}()));
			</#if>
			</#if>
			</#if>
			</#list>
		}
		getContentPane().add(messagePane);


		JPanel buttonPane = new JPanel();
		JButton ok = new JButton("U redu"); 
		JButton cancel = new JButton("Odustni"); 
		buttonPane.add(ok); 
		buttonPane.add(cancel);
		
		
		cancel.addActionListener(this);
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				${class.name} new${class.name} = new ${class.name}();
				<#list properties as p>
				<#if p.upper == 1>
				<#if p.classType??>
				<#if p.appliedStereotype?? && p.appliedStereotype =="Zoom">
				<#assign getitem = "getText()" />
				<#else>
				<#assign getitem = "getSelectedItem().toString()" />
				</#if>
				<#if p.classType.properties?first.type == "int" || p.classType.properties?first.type =="Integer">
				${p.classType.properties?first.type} ${p.name}id = Integer.parseInt(field${p.name}.${getitem});
				<#elseif p.classType.properties?first.type == "double" || p.classType.properties?first.type =="float">
				${p.classType.properties?first.type} ${p.name}id = ${p.classType.properties?first.type?cap_first}.parse${p.classType.properties?first.type?cap_first}(field${p.name}.${getitem});
				<#elseif p.classType.properties?first.type == "Date">
				${p.classType.properties?first.type} ${p.name}id = new ${p.classType.properties?first.type}();	
				<#if p.component?? && p.component == "dateChooser">
				try {
					${p.name}id = new SimpleDateFormat("dd.MM.yyyy").parse(field${p.name}.${getitem});
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				<#elseif p.component?? && p.component == "timePicker">
				try {
					${p.name}id = new SimpleDateFormat("HH:mm").parse(field${p.name}.${getitem});
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				<#else>
				try {
					${p.name}id = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(field${p.name}.${getitem});
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				</#if>
				</#if>
				new${class.name}.set${p.name?cap_first}(${p.type}DB.get${p.type}ById(${p.name}id));	
				<#elseif p.enumType??>
				${p.type} ${p.name} = null;
				<#if p.component?? && p.component == "radioButton">
				<#list p.enumType.values as value>
				<#if value != p.enumType.values?first>else </#if>if(field${value}.isSelected())
					${p.name} = ${p.type}.${value};
				</#list>
				<#else>
				${p.name} = ${p.type}.valueOf(field${p.name}.getSelectedItem().toString());
				</#if>
				new${class.name}.set${p.name?cap_first}(${p.name});
				<#else>
				<#if p.type == "int" || p.type == "Integer">
				new${class.name}.set${p.name?cap_first}(Integer.parseInt(field${p.name}.getText()));
				<#elseif (p.component?? && p.component == "checkbox") || p.type == "Boolean">
				new${class.name}.set${p.name?cap_first}(field${p.name}.isSelected());
				<#elseif p.type =="Date">
				<#if p.component?? && p.component=="dateChooser">
				new${class.name}.set${p.name?cap_first}(Date.from(field${p.name}.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				<#elseif p.component?? && p.component=="timePicker">
				new${class.name}.set${p.name?cap_first}(Date.from(field${p.name}.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant()));
				<#else>
				new${class.name}.set${p.name?cap_first}(Date.from(field${p.name}.getDateTimeStrict().atZone(ZoneId.systemDefault()).toInstant()));
				</#if>
				<#elseif p.type == "String">
				new${class.name}.set${p.name?cap_first}(field${p.name}.getText());
				<#else>
				new${class.name}.set${p.name?cap_first}(${p.type?cap_first}.parse${p.type?cap_first}(field${p.name}.getText()));
				</#if>
				</#if>
				</#if>
				</#list>
				if(${class.name?lower_case} == null) {
					${class.name}DB.save${class.name}(new${class.name});
					((${class.name}Panel)panel).addRow(new${class.name}, -1);
				}
				else {
					${class.name}DB.update${class.name}(new${class.name});
					((${class.name}Panel)panel).updateRow(new${class.name}, index);
				}
				setVisible(false); 
				MainFrame.getInstance().revalidate();
				dispose();
			}
		});
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose(); 
	}	
}
