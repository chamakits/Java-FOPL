package rogel.io.fopl.terms;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;

/**
 * A Variable is a "placeholder" Term, which can be assigned values through a
 * Substitution set. Variables also allow FOPL formulas that express quantified
 * ideas.
 * 
 * @author recardona
 */
public class Variable extends Term {

	/**
	 * Constructs a Variable with the given name. If the name is a String that did
	 * not already exist within the domain of discourse (defined as a Symbol),
	 * then a new Symbol is created.
	 * @param name the name of this Variable
	 */
	public Variable(String name) {
		super(name);
	}

	/**
	 * Constructs a Variable with the given Symbol.
	 * @param symbol the Symbol that represents this Variable within the domain of
	 * discourse
	 */	
	public Variable(Symbol symbol) {
		super(symbol);
	}

	@Override
	public Substitution unify(Unifiable unifiable, Substitution substitution) {
		
		if(this.equals(unifiable)) {
			// If they're the same, then the substitution cannot change.
			return substitution;
		}
		
		if(substitution.isBound(this)) {
			// If this Variable is bound within the parameter Substitution,
			// the unification must continue with the bound value.
			return substitution.getBinding(this).unify(unifiable, substitution);
		}
		
		// At this point, we're attempting to add a new binding. First,
		// initialize a new Substitution with the parameter Substitution.
		Substitution sigma = new Substitution(substitution);
		
		// Add the new binding and return it.
		sigma.add(this, unifiable);
		return sigma;
	}
	
	@Override
	public String toString() {
		return "[Variable: "+this.symbol.toString()+"]";
	}

	@Override
	public boolean equals(Object obj) {

		//Variable equality is composed of type and symbol equality.

		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}
		
		return true;

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
