package rogel.io.fopl;

import java.util.HashMap;

/**
 * A Symbol follows the Herbrand interpretation and is merely a (Java) String 
 * that represents itself. There is only <b>one</b> Symbol with any given name
 * (they are memory-unique objects). 
 * 
 * @author recardona
 * @see http://en.wikipedia.org/wiki/Herbrand_interpretation
 */
public class Symbol {

	/**
	 * The domain of discourse is the set of entities over which variables of
	 * interest in some formal language may range. For Symbols, this domain
	 * captures every Symbol that has been used during program execution.
	 */
	private static HashMap<String, Symbol> symbolDomainOfDiscourse = new HashMap<String, Symbol>(10000);

	/**
	 * The number to use as a suffix for Symbols generated by the method
	 * <code>Symbol.generateSymbol()</code>. 
	 */
	private static int generatedSymbolSuffix = 0;
	
	/** The String name this Symbol represents. */
	private String name;
	
	/**
	 * Returns a Symbol with the given name. If no such Symbol exists, this
	 * method creates a new one and adds it to the domain of discourse for 
	 * future retrieval.
	 * @param name the name of the Symbol that is sought.
	 * @return a Symbol with the given name.
	 * @throws IllegalArgumentException if the Symbol name is null or empty.
	 */
	public static Symbol get(String name) throws IllegalArgumentException {
		
		if((name == null) || (name.equals(""))) {
			throw new IllegalArgumentException("Attempted to get a Symbol without a name.");
		}

		if(Symbol.symbolDomainOfDiscourse.containsKey(name)) {
			return symbolDomainOfDiscourse.get(name);
		}
		
		Symbol newSymbol = new Symbol(name);
		Symbol.symbolDomainOfDiscourse.put(name, newSymbol);
		return newSymbol;
	}
	
	/**
	 * Generates a new Symbol within the domain of discourse. The new Symbol is
	 * guaranteed to be unique, and will be of the form <code>G{number}</code>,
	 * where <code>{number}</code> is an integer number.
	 * @return the newly generated Symbol.
	 */
	public static Symbol generateSymbol() {
		
		// Attempt to create an unused Symbol name.
		String generatedSymbolName = "G" + Symbol.generatedSymbolSuffix;
		
		// If that Symbol already exists, increment the counter and try again.
		while(Symbol.symbolDomainOfDiscourse.containsKey(generatedSymbolName)) {
			Symbol.generatedSymbolSuffix++;
			generatedSymbolName = "G" + Symbol.generatedSymbolSuffix;
		}
		
		// Return the new Symbol.
		return Symbol.get(generatedSymbolName);
	}
	
	/**
	 * Generates a new Symbol within the domain of discourse. The new Symbol is
	 * guaranteed to be unique, and will be of the form 
	 * <code>{prefix}{number}</code>, where <code>{prefix}</code> is the 
	 * parameter String, and <code>{number}</code> is an integer number.
	 * @param prefix the prefix of the Symbol to generate.
	 * @return the newly generated Symbol.
	 */
	public static Symbol generateSymbol(String prefix) {
		
		// Attempt to create an unused Symbol name.
		String generatedSymbolName = prefix + Symbol.generatedSymbolSuffix;
		
		// If that Symbol already exists, increment the counter and try again.
		while(Symbol.symbolDomainOfDiscourse.containsKey(generatedSymbolName)) {
			Symbol.generatedSymbolSuffix++;
			generatedSymbolName = prefix + Symbol.generatedSymbolSuffix;
		}
		
		// Return the new Symbol.
		return Symbol.get(generatedSymbolName);
	}
	
	/**
	 * Constructs a Symbol of the given name.
	 * @param name the name of the symbol
	 */
	private Symbol(String name) {
		this.name = name;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Symbol.clone() is not supported");
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( ! (obj instanceof Symbol)) {
			return false;
		}
		
		Symbol other = (Symbol) obj;
		return (this.name.equals(other.name));
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
