package hr.fer.zemris.java.custom.scripting.exec.functions;

import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;

/**
 * A factory class which creates a new {@linkplain IFunction} implementation
 * based on the given function element.
 * 
 * @author Ivan Anić
 * @version 1.0
 */
public class FunctionFactory {

	/**
	 * Creates a new {@linkplain IFunction} implementation based on the given
	 * function element.
	 * 
	 * @param e
	 *            the {@linkplain ElementFunction} instance
	 * @return the desired function
	 */
	public static IFunction create(ElementFunction e) {
		switch (e.getFunctionName()) {
		case ("sin"):
			return new FunctionSine();
		case ("decfmt"):
			return new FunctionDecimalFormat();
		case ("dup"):
			return new FunctionDuplicate();
		case ("swap"):
			return new FunctionSwap();
		case ("setMimeType"):
			return new FunctionSetMimeType();
		case ("paramGet"):
			return new FunctionParamGet();
		case ("pparamGet"):
			return new FunctionPParamGet();
		case ("pparamSet"):
			return new FunctionPParamSet();
		case ("pparamDel"):
			return new FunctionPParamDel();
		case ("tparamGet"):
			return new FunctionTParamGet();
		case ("tparamSet"):
			return new FunctionTParamSet();
		case ("tparamDel"):
			return new FunctionTParamDel();
		default:
			return null;
		}
	}
}
