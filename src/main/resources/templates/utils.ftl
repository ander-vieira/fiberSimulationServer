<#macro generateSelectVue elementsList modelVar>
    <select v-model="${modelVar}">
        <#list elementsList as element>
            <option>${element}</option>
        </#list>
    </select>
</#macro>