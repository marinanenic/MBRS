package ${class.typePackage}.${package};

import java.util.List;
<#assign date = false>
<#list properties as p>
<#if p.type == "Date" && !date && p.searchBy>
import java.util.Date;
<#assign date = true>
<#elseif p.classType?? && p.upper == 1>
<#list p.classType.properties as pro>
<#if pro.type == "Date" && !date && pro.searchChild>
import java.util.Date;
<#assign date = true>
</#if>
</#list>
</#if>
</#list>

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.AnnotationConfiguration;

import ${class.typePackage}.*;

public class ${class.name}DB {
	private static SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();

	@SuppressWarnings("unchecked")
	public static List<${class.name}> get${class.label}(){
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		List<${class.name}> results = session.createQuery("from ${class.name}").list(); 
		t.commit();
		session.close();  
		return results;
	}
	
	public static void save${class.name}(${class.name} ${class.name?lower_case}) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.persist(${class.name?lower_case});
		t.commit();
		session.close(); 
	}
	
	public static ${class.name} get${class.name}ById(${properties?first.type} id) {
		Session session=factory.openSession();
		${class.name} ${class.name?lower_case} = (${class.name})session.get(${class.name}.class, id);
		session.close();  
		return ${class.name?lower_case};
	}
	
	<#assign first = true>
	@SuppressWarnings("unchecked")
	public static List<${class.name}> search${class.label}(<#list class.properties as p><#rt>
	<#if p.upper==1 && p.classType??><#rt>
	<#list p.classType.properties as pro><#rt>
	<#if pro.searchChild><#rt>
<#if !first>, <#else><#assign first = false></#if><#if pro.type == "String" || pro.enumType?? || pro.type == "Boolean">${pro.type} ${p.name}${pro.name}<#rt>
<#else><#if pro.type == "int">Integer ${p.name}${pro.name}od, Integer<#else>${pro.type?cap_first} ${p.name}${pro.name}od, ${pro.type?cap_first}</#if> ${p.name}${pro.name}do</#if><#rt>
<#elseif pro.id><#if !first>, <#else><#assign first = false></#if><#if pro.type == "int">Integer<#else>${pro.type?cap_first}</#if> ${p.name}${pro.name}</#if></#list><#elseif p.searchBy><#rt>
<#if !first>, <#else><#assign first = false></#if><#if p.type == "String" || p.enumType?? || p.type == "Boolean">${p.type} ${p.name}<#else>${p.type} ${p.name}od, ${p.type} ${p.name}do</#if></#if></#list>) {
	List<${class.name}> results = null;
	Session session=factory.openSession();
	Criteria c = session.createCriteria(${class.name}.class, "${class.name?lower_case}");
	<#list class.properties as p>
	<#if p.upper==1 && p.classType??>
	<#list p.classType.properties as pro>
	<#if pro.searchChild>
	c.createAlias("${class.name?lower_case}.${p.name}", "${p.name}");
	<#if pro.type == "String">
	if(${p.name}${pro.name} != null)
		c.add(Restrictions.like("${p.name}.${pro.name}", "%"+${p.name}${pro.name}+"%").ignoreCase());
	<#elseif pro.enumType?? || pro.type == "Boolean">
	if(${p.name}${pro.name} != null)
		c.add(Restrictions.eq("${p.name}.${pro.name}", ${p.name}${pro.name}));
	<#else>
	if(${p.name}${pro.name}od != null)
		c.add(Restrictions.ge("${p.name}.${pro.name}", ${p.name}${pro.name}od));
	if(${p.name}${pro.name}do != null)
		c.add(Restrictions.le("${p.name}.${pro.name}", ${p.name}${pro.name}do));
	</#if>
	<#elseif pro.id>
	if(${p.name}${pro.name} != null)
		c.add(Restrictions.eq("${p.name}.${pro.name}", ${p.name}${pro.name}));
	</#if>
	</#list>
	<#elseif p.searchBy>
	<#if p.type == "String">
	if(${p.name} != null)
		c.add(Restrictions.like("${p.name}", "%"+${p.name}+"%").ignoreCase());
	<#elseif p.enumType?? || p.type == "Boolean">
	if(${p.name} != null)
		c.add(Restrictions.eq("${p.name}", ${p.name}));
	<#else>
	if(${p.name}od != null)
		c.add(Restrictions.ge("${p.name}", ${p.name}od));
	if(${p.name}do != null)
		c.add(Restrictions.le("${p.name}", ${p.name}do));
	</#if>
	</#if>
	</#list>
	results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	return results;
	}
	
	public static void update${class.name}(${class.name} ${class.name?lower_case}) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		session.merge(${class.name?lower_case});
		t.commit();
	}
	
	public static void delete${class.name}(${properties?first.type} id) {
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		${class.name} result = get${class.name}ById(id);
		session.delete(result);
		t.commit();
	}
	
	<#list class.properties as p>
	<#if p.classType?? && p.upper == 1>
	@SuppressWarnings("unchecked")
	public static List<${class.name}> get${class.label}From${p.type}(${p.classType.properties?first.type} ${p.type?lower_case}_id) {
		List<${class.name}> results = null;
		Session session=factory.openSession();
		Criteria c = session.createCriteria(${class.name}.class, "${class.name?lower_case}");
		c.createAlias("${class.name?lower_case}.${p.name}", "${p.name}");
		c.add(Restrictions.eq("${p.name}.${p.type?lower_case}_id", ${p.type?lower_case}_id));
		results = c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return results;
	}
	
	</#if>
	</#list>
}
