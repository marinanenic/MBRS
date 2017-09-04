<?xml version='1.0' encoding='UTF-8'?>  
<!DOCTYPE hibernate-configuration PUBLIC  
          "-//Hibernate/Hibernate Configuration DTD 3.0//EN"  
          "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">  
  
<hibernate-configuration>  
  
<session-factory>  
 <property name="hbm2ddl.auto">update</property>  
 <property name="dialect">org.hibernate.dialect.Oracle9Dialect</property>  
 <property name="connection.url">jdbc:oracle:thin:@localhost:1521:xe</property>  
 <property name="connection.username">marina</property>  
 <property name="connection.password">marina</property>  
 <property name="connection.driver_class">oracle.jdbc.driver.OracleDriver</property>  
 
 <#list classes as c>     
 <mapping class="${c.typePackage}.${c.name}"/>
 </#list>

 </session-factory>  
  
</hibernate-configuration>  