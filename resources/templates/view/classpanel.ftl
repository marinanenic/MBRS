package ${class.typePackage}.${package};

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<#assign imported = false />
<#assign importedpicker = false />
<#list properties as p>
<#if p.upper == 1>
<#if p.classType??>
import ${p.classType.typePackage}.${p.type};
<#list p.classType.properties as pro> 
<#if pro.appliedStereotype?? && pro.appliedStereotype == "Lookup">
<#if pro.type == "Date">
<#if !imported>
import java.text.DateFormat;
import java.text.SimpleDateFormat;
<#assign imported = true />
</#if>
<#if !importedpicker && pro.searchChild>
import java.util.Date;
import java.time.ZoneId;
import com.github.lgooddatepicker.components.*;
<#assign importedpicker = true />
</#if>
<#elseif pro.enumType?? && pro.searchChild>
import ${class.typePackage}.${p.type};
</#if>
</#if>
</#list>
<#elseif p.enumType?? && p.searchBy>
import ${class.typePackage}.${p.type};
<#else>
<#if p.type == "Date">
<#if !imported>
import java.text.DateFormat;
import java.text.SimpleDateFormat;
<#assign imported = true />
</#if>
<#if !importedpicker && p.searchBy>
import java.util.Date;
import java.time.ZoneId;
import com.github.lgooddatepicker.components.*;
<#assign importedpicker = true />
</#if>
</#if>
</#if>
</#if>
</#list>
import java.util.Vector;
<#if search>
import java.util.List;
import ${class.typePackage}.database.${class.name}DB;
</#if>

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import ${class.typePackage}.actions.AddEditAction;
import ${class.typePackage}.actions.${class.name}DeleteAction;
<#list importActions as a>
import ${class.typePackage}.actions.${a}ChildAction;
</#list>
import ${class.typePackage}.${class.name};

@SuppressWarnings("serial")
public class ${class.name}Panel extends JPanel{
	private JTable table;
	<#list class.operations as o>
	protected JButton button${o};
	</#list>
	<#assign importeddate = false />
	<#assign importedtime = false />
	<#assign imported = false />
	<#list properties as p>
		<#if p.upper == 1>
			<#if p.classType??>
				<#list p.classType.properties as pro> 
					<#if pro.appliedStereotype?? && pro.appliedStereotype == "Lookup" && pro.type == "Date">
						<#if pro.component?? && pro.component =="dateChooser" && !importeddate>
	DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
						<#elseif pro.component?? && pro.component =="timePicker" && !importedtime>
	DateFormat time = new SimpleDateFormat("HH:mm");
						<#elseif !imported>
	DateFormat datetime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
						</#if>
						<#assign imported = true />
					</#if>
				</#list>
				<#else>
					<#if p.type == "Date">
						<#if p.component?? && p.component =="dateChooser"  && !importeddate>
	DateFormat date = new SimpleDateFormat("dd.MM.yyyy");
						<#elseif p.component?? && p.component =="timePicker"  && !importedtime>
	DateFormat time = new SimpleDateFormat("HH:mm");
						<#elseif !imported>
	DateFormat datetime = new SimpleDateFormat("dd.MM.yyyy HH:mm");
						<#assign imported = true />
					</#if>
				</#if>
			</#if>
		</#if>
	</#list>
	
	public ${class.name}Panel(List<${class.name}> ${class.label?lower_case}list, int index, Object parent){
		super(new BorderLayout());
		initGui(${class.label?lower_case}list, index, parent);
	}
	
	protected void initGui(List<${class.name}> ${class.label?lower_case}list, int index, Object parent) {
	
		JPanel top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel naziv = new JLabel("${class.label}");
		naziv.setPreferredSize(new Dimension(150, naziv.getPreferredSize().height));
		top.add(naziv);
		<#if class.add>
		JButton add = new JButton(new AddEditAction("Dodaj", "${class.typePackage}.view.${class.name}Dialog", null, -1, parent, this));
		top.add(add);
		</#if>
		<#if class.update>
		JButton edit = new JButton("Izmijeni");
		edit.setEnabled(false);
		top.add(edit);
		</#if>
		<#if class.delete>
		JButton delete = new JButton("Obri�i");
		delete.setEnabled(false);
		top.add(delete);
		</#if>
		
		Vector<String> columnNames = new Vector<String>();
		<#list properties as p>
		<#if p.upper == 1>
		<#if p.classType??>
		<#list p.classType.properties as pro> 
		<#if pro.appliedStereotype?? && pro.appliedStereotype == "Lookup">
		columnNames.add("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if>(${p.name?cap_first})");
		</#if>
		</#list>
		<#else>
		columnNames.add("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>");
		</#if>		
		</#if>
		</#list>
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		
		<#if (class.operations?size > 0)>
		JPanel operations = new JPanel();
		operations.setLayout(new BoxLayout(operations, BoxLayout.Y_AXIS));
		operations.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Izvje�taji", TitledBorder.LEFT, TitledBorder.TOP));
		
		<#list class.operations as o>
		button${o} = new JButton("${o?cap_first}");
		button${o}.setMaximumSize(new Dimension(Integer.MAX_VALUE, button${o}.getPreferredSize().height) );		
		button${o}.setAlignmentX(LEFT_ALIGNMENT);
		set${o?cap_first}Action();
		operations.add(Box.createRigidArea(new Dimension(0,10)));
		operations.add(button${o});
		
		</#list>
		JComponent c = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c.setAlignmentX(LEFT_ALIGNMENT);
		operations.add(c);
		right.add(operations);
		</#if>
		
		JPanel connections = new JPanel();
		connections.setLayout(new BoxLayout(connections, BoxLayout.Y_AXIS));
		connections.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Veze", TitledBorder.LEFT, TitledBorder.TOP));
		
		<#list class.properties as p>
		<#if p.classType?? && p.upper == -1 && p.appliedStereotype?? && p.appliedStereotype == "Child">
		JButton button${p.name} = new JButton("${p.name?cap_first}");
		button${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, button${p.name}.getPreferredSize().height) );		
		button${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		button${p.name}.setEnabled(false);
		connections.add(Box.createRigidArea(new Dimension(0,10)));
		connections.add(button${p.name});
		</#if>
		</#list>
		JComponent c1 = (JComponent) Box.createRigidArea(new Dimension(300,10));
		c1.setAlignmentX(LEFT_ALIGNMENT);
		connections.add(c1);
		right.add(connections);
		
		table = new JTable(new NonEditableTableModel(new Vector<Vector<Object>>(), columnNames));
		replaceTableContent(${class.label?lower_case}list);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel thispanel = this;
		
		<#if class.update || class.delete>
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					<#if class.update>
					edit.setEnabled(false);
					</#if>
					<#if class.delete>
					delete.setEnabled(false);
					</#if>
					<#list class.properties as p>
					<#if p.classType?? && p.upper == -1>
					button${p.name}.setEnabled(false);
					</#if>
					</#list>
				}
				else {
					<#if class.update>
					edit.setEnabled(true);
					edit.setAction(new AddEditAction("Izmijeni", "${class.typePackage}.view.${class.name}Dialog", (${properties?first.type})table.getValueAt(selectedRow, 0), selectedRow, parent, thispanel));
					</#if>
					<#if class.delete>
					delete.setEnabled(true);
					delete.setAction(new ${class.name}DeleteAction("Obri�i", (${properties?first.type})table.getValueAt(selectedRow, 0),selectedRow));
					</#if>
					<#list class.properties as p>
					<#if p.classType?? && p.upper == -1 && p.appliedStereotype?? && p.appliedStereotype == "Child">
					button${p.name}.setEnabled(true);
					button${p.name}.setAction(new ${p.type}ChildAction("${p.name?cap_first}"<#list p.classType.properties as pro><#rt>
<#if pro.classType?? && pro.upper == 1>, <#if pro.classType.properties?first.type == "int">(Integer)<#rt>
<#else>(${properties?first.type?cap_first})</#if><#if pro.type == class.name>table.getValueAt(selectedRow, 0)<#else>null</#if></#if></#list>));
					</#if>
					</#list>
				}
					
			}
		});
		</#if>
		if(index!=-1)
			table.setRowSelectionInterval(index, index);
		JScrollPane ${class.label?lower_case} = new JScrollPane(table);
		
		<#if search>
		JPanel search = new JPanel();
		search.setPreferredSize(new Dimension(300,200));
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		search.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pretraga", TitledBorder.LEFT, TitledBorder.TOP));
		
		<#list properties as p>
		<#if p.upper == 1 && p.classType??>
		<#list p.classType.properties as pro>
		<#if pro.searchChild>
		<#if pro.type == "String">
		JLabel label${p.name}${pro.name} = new JLabel("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if>(<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>):");
		label${p.name}${pro.name}.setAlignmentX(LEFT_ALIGNMENT);
		JTextField field${p.name}${pro.name} = new JTextField();
		field${p.name}${pro.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}${pro.name}.getPreferredSize().height) );		
		field${p.name}${pro.name}.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name}${pro.name});
		search.add(field${p.name}${pro.name});
		<#elseif pro.type == "Boolean">
		JLabel label${p.name}${pro.name} = new JLabel("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if>(<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>):");
		JCheckBox field${p.name}${pro.name}true = new JCheckBox("Da");
		JCheckBox field${p.name}${pro.name}false = new JCheckBox("Ne");
		field${p.name}${pro.name}true.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(field${p.name}${pro.name}true.isSelected())
					field${p.name}${pro.name}false.setSelected(false);
			}
		});
		field${p.name}${pro.name}false.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(field${p.name}${pro.name}false.isSelected())
					field${p.name}${pro.name}true.setSelected(false);
			}
		});
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name}${pro.name});
		search.add(field${p.name}${pro.name}true);
		search.add(field${p.name}${pro.name}false);
		<#elseif pro.enumType??>
		JLabel label${p.name}${pro.name} = new JLabel("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if>(<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>):");
		JComboBox<String> field${p.name}${pro.name} = new JComboBox<String>();
		field${p.name}${pro.name}.addItem("");
		for(${pro.type} ${p.name}${pro.name}: ${pro.type}.values())
			field${p.name}${pro.name}.addItem(${p.name}${pro.name}.toString());
		field${p.name}${pro.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}${pro.name}.getPreferredSize().height));
		field${p.name}${pro.name}.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name}${pro.name});
		search.add(field${p.name}${pro.name});
		<#else>
		JLabel od${p.name}${pro.name}Label = new JLabel("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if> od(<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>):");
		od${p.name}${pro.name}Label.setAlignmentX(LEFT_ALIGNMENT);
		JLabel do${p.name}${pro.name}Label = new JLabel("<#if pro.label??>${pro.label}<#else>${pro.name?cap_first}</#if> do(<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>):");
		do${p.name}${pro.name}Label.setAlignmentX(LEFT_ALIGNMENT);
		<#if pro.type == "Date">
		<#if pro.component?? && pro.component == "dateChooser">
		DatePicker field${pro.name}od = new DatePicker();	
		DatePicker field${pro.name}do = new DatePicker();	
		<#elseif pro.component?? && pro.component == "timePicker">
		TimePicker field${pro.name}od = new TimePicker();
		TimePicker field${pro.name}do = new TimePicker();
		<#else>
		DateTimePicker field${pro.name}od = new DateTimePicker();
		DateTimePicker field${pro.name}do = new DateTimePicker();
		</#if>
		field${pro.name}od.setAlignmentX(LEFT_ALIGNMENT);
		field${pro.name}od.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${pro.name}od.getPreferredSize().height) );	
		<#else>
		JTextField field${p.name}${pro.name}od = new JTextField();
		field${p.name}${pro.name}od.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}${pro.name}od.getPreferredSize().height) );		
		field${p.name}${pro.name}od.setAlignmentX(LEFT_ALIGNMENT);
		JTextField field${p.name}${pro.name}do = new JTextField();
		</#if>		
		field${p.name}${pro.name}do.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}${pro.name}do.getPreferredSize().height) );		
		field${p.name}${pro.name}do.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(od${p.name}${pro.name}Label);
		search.add(field${p.name}${pro.name}od);
		search.add(do${p.name}${pro.name}Label);
		search.add(field${p.name}${pro.name}do);
		</#if>
		</#if>
		</#list>
		<#else>
		<#if p.searchBy>
		<#if p.type == "String">
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		label${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		JTextField field${p.name} = new JTextField();
		field${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}.getPreferredSize().height) );		
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name});
		search.add(field${p.name});
		<#elseif p.type == "Boolean">
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		JCheckBox field${p.name}true = new JCheckBox("Da");
		JCheckBox field${p.name}false = new JCheckBox("Ne");
		field${p.name}true.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(field${p.name}true.isSelected())
					field${p.name}false.setSelected(false);
			}
		});
		field${p.name}false.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(field${p.name}false.isSelected())
					field${p.name}true.setSelected(false);
			}
		});
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name});
		search.add(field${p.name}true);
		search.add(field${p.name}false);
		<#elseif p.enumType??>
		JLabel label${p.name} = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if>:");
		JComboBox<String> field${p.name} = new JComboBox<String>();
		field${p.name}.addItem("");
		for(${p.type} ${p.name}: ${p.type}.values())
			field${p.name}.addItem(${p.name}.toString());
		field${p.name}.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}.getPreferredSize().height));
		field${p.name}.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(label${p.name});
		search.add(field${p.name});
		<#else>
		JLabel od${p.name}Label = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if> od:");
		od${p.name}Label.setAlignmentX(LEFT_ALIGNMENT);
		JLabel do${p.name}Label = new JLabel("<#if p.label??>${p.label}<#else>${p.name?cap_first}</#if> do:");
		do${p.name}Label.setAlignmentX(LEFT_ALIGNMENT);
		<#if p.type == "Date">
		<#if p.component?? && p.component == "dateChooser">
		DatePicker field${p.name}od = new DatePicker();	
		DatePicker field${p.name}do = new DatePicker();	
		<#elseif p.component?? && p.component == "timePicker">
		TimePicker field${p.name}od = new TimePicker();
		TimePicker field${p.name}do = new TimePicker();
		<#else>
		DateTimePicker field${p.name}od = new DateTimePicker();
		DateTimePicker field${p.name}do = new DateTimePicker();
		</#if>
		field${p.name}od.setAlignmentX(LEFT_ALIGNMENT);
		field${p.name}od.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}od.getPreferredSize().height) );
		<#else>
		JTextField field${p.name}od = new JTextField();
		field${p.name}od.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}od.getPreferredSize().height) );		
		field${p.name}od.setAlignmentX(LEFT_ALIGNMENT);
		JTextField field${p.name}do = new JTextField();
		</#if>		
		field${p.name}do.setMaximumSize(new Dimension(Integer.MAX_VALUE, field${p.name}do.getPreferredSize().height) );		
		field${p.name}do.setAlignmentX(LEFT_ALIGNMENT);
		search.add(Box.createRigidArea(new Dimension(0,10)));
		search.add(od${p.name}Label);
		search.add(field${p.name}od);
		search.add(do${p.name}Label);
		search.add(field${p.name}do);
		</#if>
		</#if>
		</#if>
		</#list>
		
		search.add(Box.createRigidArea(new Dimension(0,20)));
		
		JPanel buttons = new JPanel();
		JButton pretrazi = new JButton("Pretra�i");
		pretrazi.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				<#list class.properties as p>
				<#if p.upper==1 && p.classType??>
				<#list p.classType.properties as pro>
				<#if pro.searchChild>
				<#if pro.type == "String">
				${pro.type} ${p.name}${pro.name} = null;
				if(!field${p.name}${pro.name}.getText().trim().isEmpty())
					${p.name}${pro.name} = field${p.name}${pro.name}.getText().trim();
				<#elseif pro.enumType??>
				${pro.type} ${p.name}${pro.name} = null;
				if(field${p.name}${pro.name}.getSelectedIndex() != 0)
					${p.name}${pro.name} = ${pro.type}.valueOf(field${p.name}${pro.name}.getSelectedItem().toString());
				<#elseif pro.type == "Boolean">
				${pro.type} ${p.name}${pro.name} = null;
				if(field${p.name}${pro.name}true.isSelected())
					${p.name}${pro.name} = true;
				else if(field${p.name}${pro.name}false.isSelected())
					${p.name}${pro.name} = false;
				<#elseif pro.type == "Date">
				Date ${p.name}${pro.name}od = null;
				Date ${p.name}${pro.name}do = null;
				<#if pro.component?? && pro.component == "dateChooser">
				if (field${p.name}${pro.name}od.getDate()!=null)
					${p.name}${pro.name}od = Date.from(field${p.name}${pro.name}od.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				if (field${p.name}${pro.name}do.getDate()!=null)
					${p.name}${pro.name}do = Date.from(field${p.name}${pro.name}do.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				<#elseif pro.component?? && pro.component == "timePicker">
				if (field${p.name}${pro.name}od.getTime()!=null)
					${p.name}${pro.name}od = Date.from(${p.name}${pro.name}od.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
				if (field${p.name}${pro.name}do.getTime()!=null)
					${p.name}${pro.name}do = Date.from(${p.name}${pro.name}do.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
				<#else>
				if (field${p.name}${pro.name}od.getDateTimeStrict()!=null)
					${p.name}${pro.name}od = Date.from(field${p.name}${pro.name}od.getDateTimeStrict().atZone(ZoneId.systemDefault()).toInstant());
				if (field${p.name}${pro.name}do.getDateTimeStrict()!=null)
					${p.name}${pro.name}do = Date.from(field${p.name}${pro.name}do.getDateTimeStrict().atZone(ZoneId.systemDefault()).toInstant());
				</#if>
				<#elseif pro.type == "int">
				int ${p.name}${pro.name} = (Integer)null;
				if(!field${p.name}${pro.name}od.getText().trim().isEmpty())
					${p.name}${pro.name}od = Integer.parseInt(field${p.name}${pro.name}od.getText().trim());
				if(!field${p.name}${pro.name}do.getText().trim().isEmpty())
					${p.name}${pro.name}do = Integer.parseInt(field${p.name}${pro.name}do.getText().trim());
				<#else>
				${pro.type} ${p.name}${pro.name} = (${pro.type?cap_first})null;
				if(!field${p.name}${pro.name}od.getText().trim().isEmpty())
					${p.name}${pro.name}od = ${pro.type?cap_first}.parse${pro.type?cap_first}(field${p.name}${pro.name}od.getText().trim());
				if(!field${p.name}${pro.name}do.getText().trim().isEmpty())
					${p.name}${pro.name}do = ${pro.type?cap_first}.parse${pro.type?cap_first}(field${p.name}${pro.name}do.getText().trim());
				</#if>
				<#elseif pro.id>
				<#if pro.type == "int">Integer<#else>${pro.type?cap_first}</#if> ${p.name}${pro.name} = (<#if pro.type == "int">Integer<#else>${pro.type?cap_first}</#if>)null;
				if(parent!=null && parent.getClass().equals(${p.type}.class))
					${p.name}${pro.name} = ((${p.type})parent).get${pro.name?cap_first}();
				</#if>
				</#list>
				<#elseif p.searchBy>
				<#if p.type == "String">
				${p.type} ${p.name} = null;
				if(!field${p.name}.getText().trim().isEmpty())
					${p.name} = field${p.name}.getText().trim();
				<#elseif p.enumType??>
				${p.type} ${p.name}= null;
				if(field${p.name}.getSelectedIndex() != 0)
					${p.name} = ${p.type}.valueOf(field${p.name}.getSelectedItem().toString());
				<#elseif p.type == "Boolean">
				${p.type} ${p.name} = null;
				if(field${p.name}true.isSelected())
					${p.name} = true;
				else if(field${p.name}false.isSelected())
					${p.name} = false;
				<#elseif p.type == "Date">
				Date ${p.name}od = null;
				Date ${p.name}do = null;
				<#if p.component?? && p.component == "dateChooser">
				if (field${p.name}od.getDate()!=null)
					${p.name}od = Date.from(field${p.name}od.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				if (field${p.name}do.getDate()!=null)
					${p.name}do = Date.from(field${p.name}do.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
				<#elseif p.component?? && p.component == "timePicker">
				if (field${p.name}od.getTime()!=null)
					${p.name}od = Date.from(${p.name}od.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
				if (field${p.name}do.getTime()!=null)
					${p.name}do = Date.from(${p.name}do.getTime().atDate(LocalDate.of(1, 1, 1)).atZone(ZoneId.systemDefault()).toInstant());
				<#else>
				if (field${p.name}od.getDateTimeStrict()!=null)
					${p.name}od = Date.from(field${p.name}od.getDateTimeStrict().atZone(ZoneId.systemDefault()).toInstant());
				if (field${p.name}do.getDateTimeStrict()!=null)
					${p.name}do = Date.from(field${p.name}do.getDateTimeStrict().atZone(ZoneId.systemDefault()).toInstant());
				</#if>
				<#elseif p.type == "int">
				int ${p.name} = (Integer)null;
				if(!field${p.name}od.getText().trim().isEmpty())
					${p.name}od = Integer.parseInt(field${p.name}od.getText().trim());
				if(!field${p.name}do.getText().trim().isEmpty())
					${p.name}do = Integer.parseInt(field${p.name}do.getText().trim());
				<#else>
				${p.type} ${p.name} = (${p.type?cap_first})null;
				if(!field${p.name}od.getText().trim().isEmpty())
					${p.name}od = ${p.type?cap_first}.parse${p.type?cap_first}(field${p.name}od.getText().trim());
				if(!field${p.name}do.getText().trim().isEmpty())
					${p.name}do = ${p.type?cap_first}.parse${p.type?cap_first}(field${p.name}do.getText().trim());
				</#if>
				</#if>
				</#list>
				<#assign first = true>
				List<${class.name}> results = ${class.name}DB.search${class.label}(<#list class.properties as p><#if p.upper==1 && p.classType??><#rt>
				<#list p.classType.properties as pro><#rt>
				<#if pro.searchChild || pro.id><#rt>
<#if !first>, <#else><#assign first = false></#if><#if pro.type == "String" || pro.enumType?? || pro.type == "Boolean" || pro.id>${p.name}${pro.name}<#else>${p.name}${pro.name}od, ${p.name}${pro.name}do</#if><#rt>
				</#if></#list><#elseif p.searchBy><#rt>
<#if !first>, <#else><#assign first = false></#if><#if p.type == "String" || p.enumType?? || p.type == "Boolean">${p.name}<#else>${p.name}od, ${p.name}do</#if></#if></#list>);
				replaceTableContent(results);
			}
		});
		buttons.add(pretrazi);
		JButton ponisti = new JButton("Poni�ti");
		ponisti.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				<#list properties as p>
				<#if p.upper == 1 && p.classType??>
				<#list p.classType.properties as pro>
				<#if pro.searchChild>
				<#if pro.type == "String">
				field${p.name}${pro.name}.setText(null);
				<#elseif pro.type == "Boolean">
				field${p.name}${pro.name}true.setSelected(false);
				field${p.name}${pro.name}false.setSelected(false);
				<#elseif pro.enumType??>
				field${p.name}${pro.name}.setSelectedIndex(0);
				<#else>
				<#if pro.type == "Date">
				<#if pro.component?? && pro.component == "dateChooser">
				field${p.name}${pro.name}od.setDate(null);
				field${p.name}${pro.name}do.setDate(null);
				<#elseif pro.component?? && pro.component=="timePicker">
				field${p.name}${pro.name}od.setTime(null);
				field${p.name}${pro.name}do.setTime(null);
				<#else>
				field${p.name}${pro.name}od.setDateTimeStrict(null);
				field${p.name}${pro.name}do.setDateTimeStrict(null);
				</#if>
				<#else>
				field${p.name}${pro.name}od.setText(null);
				field${p.name}${pro.name}do.Text(null);
				</#if>
				</#if>
				</#if>
				</#list>
				<#else>
				<#if p.searchBy>
				<#if p.type == "String">
				field${p.name}.setText(null);
				<#elseif p.type == "Boolean">
				field${p.name}true.setSelected(false);
				field${p.name}false.setSelected(false);
				<#elseif p.enumType??>
				field${p.name}.setSelectedIndex(0);
				<#else>
				<#if p.type == "Date">
				<#if p.component?? && p.component == "dateChooser">
				field${p.name}od.setDate(null);
				field${p.name}do.setDate(null);
				<#elseif p.component?? && p.component=="timePicker">
				field${p.name}od.setTime(null);
				field${p.name}do.setTime(null);
				<#else>
				field${p.name}od.setDateTimeStrict(null);
				field${p.name}do.setDateTimeStrict(null);
				</#if>
				<#else>
				field${p.name}od.setText(null);
				field${p.name}do.Text(null);
				</#if>
				</#if>
				</#if>
				</#if>
				</#list>
			}
		});
		buttons.add(ponisti);
		buttons.setAlignmentX(LEFT_ALIGNMENT);
		search.add(buttons);
		
		right.add(search);
		</#if>
		
		this.add(right, BorderLayout.EAST);
		this.add(top, BorderLayout.NORTH);
		this.add(${class.label?lower_case},BorderLayout.CENTER);		
	}
	
	public void replaceTableContent (List<${class.name}> ${class.label?lower_case}) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (${class.name} ${class.name?lower_case} : ${class.label?lower_case}){
			addRow(${class.name?lower_case}, -1);
		}
	}
	
	public void addRow(${class.name} ${class.name?lower_case}, int i) {
		Vector<Object> v = new Vector<Object>();
		<#list properties as p>
		<#if p.upper == 1>
		<#if p.classType??>
		<#list p.classType.properties as pro> 
		<#if pro.appliedStereotype?? && pro.appliedStereotype == "Lookup">
		v.add(<#if pro.type =="Date"><#rt>
<#if pro.component?? && pro.component == "dateChooser"><#rt>
date.format<#elseif pro.component?? && pro.component == "timePicker"><#rt>
time.format<#else><#rt>
datetime.format</#if></#if>(${class.name?lower_case}.get${p.name?cap_first}().get${pro.name?cap_first}()));
		</#if>
		</#list>
		<#else>
		v.add(<#if p.type =="Date"><#rt>
		<#if p.component?? && p.component == "dateChooser"><#rt>
date.format<#elseif p.component?? && p.component == "timePicker"><#rt>
time.format<#else><#rt>
datetime.format</#if></#if>(${class.name?lower_case}.get${p.name?cap_first}()));
		</#if>
		</#if>
		</#list>
		if (i != -1)
			((DefaultTableModel)table.getModel()).insertRow(i,v);
		else
			((DefaultTableModel)table.getModel()).addRow(v);
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	public void deleteRow (int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	public void updateRow (${class.name} ${class.name?lower_case}, int i) {
		((DefaultTableModel)table.getModel()).removeRow(i);
		addRow(${class.name?lower_case}, i);
		table.setRowSelectionInterval(i, i);
	}
	
	<#list class.operations as o>
	public void set${o?cap_first}Action(){}
	
	</#list>

}
