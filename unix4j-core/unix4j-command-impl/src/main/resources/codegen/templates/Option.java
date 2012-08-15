<@pp.dropOutputFile /> 
<#list commandDefs as def> 
<#global cmd=def.command>
<#global simpleName=cmd.simpleName+"Option">
<@pp.changeOutputFile name=pp.pathTo(def.pkg.path+"/"+simpleName+".java")/> 
package ${def.pkg.name};

import java.util.EnumSet;
import org.unix4j.optset.Option;
import org.unix4j.optset.OptionSet;

/**
 * Options for the {@link ${cmd.pkg.name}.${cmd.simpleName} ${def.commandName}} command.
 * Application code should normally use {@link ${cmd.pkg.name}.${cmd.simpleName}#Options ${cmd.simpleName}.Options} instead.
 */
public enum ${simpleName} implements Option, OptionSet<${simpleName}> {
	<#foreach opt in def.options?values>
	/**
	 * Option <b>{@code --${opt.name}}</b>, <b>{@code -${opt.acronym}}</b>: 
	 * ${opt.desc}
	 */
	${opt.name}('${opt.acronym}')<#if opt_has_next>,<#else>;</#if>
	</#foreach>
	
	private final char acronym;
	private ${simpleName}(char acronym) {
		this.acronym = acronym;
	}
	/**
	 * Returns the option with the given {@code acronym}, or {@code null} if no
	 * such option is found.
	 * 
	 * @param acronym the option {@link #acronym() acronym}
	 * @return	the option with the given {@code acronym} or {@code null} if it
	 * 			is not found
	 */
	public static ${simpleName} findByAcronym(char acronym) {
		for (final ${simpleName} opt : values()) {
			if (opt.acronym() == acronym) return opt;
		}
		return null;
	}
	@Override
	public char acronym() {
		return acronym;
	}
	@Override
	public boolean isSet(${simpleName} option) {
		return equals(option);
	}
	/**
	 * Returns a new set with {@code this} active option.
	 * 
	 * @return a new set containing this option
	 */
	@Override
	public EnumSet<${simpleName}> asSet() {
		return EnumSet.of(this);
	}
	/**
	 * Returns true if the {@link Option#acronym() acronym} should be used in
	 * for the specified {@code option} string representations. 
	 * <p>
	 * This method returns always true for all options.
	 *  
	 * @param option
	 *            the option of interest
	 * @return always true indicating that option acronyms should be used in
	 * 			string representations for all options
	 */
	@Override
	public boolean useAcronymFor(${simpleName} option) {
		return true;
	}
}
</#list>