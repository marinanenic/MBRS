package ${class.typePackage};

public enum ${class.name} {  
<#list properties as property>
	${property}<#if property != properties?last>,</#if>   
</#list>
}