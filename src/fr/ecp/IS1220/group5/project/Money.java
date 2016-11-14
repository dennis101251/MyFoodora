package fr.ecp.IS1220.group5.project;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Locale;

public class Money extends BigDecimal {
	
	public Money(char[] in) {
		super(in);
	}

	public Money(String val) {
		super(val);
	}

	public Money(double val) {
		super(val);
	}

	public Money(BigInteger val) {
		super(val);
	}

	public Money(int val) {
		super(val);
	}

	public Money(long val) {
		super(val);
	}

	public Money(char[] in, MathContext mc) {
		super(in, mc);
	}

	public Money(String val, MathContext mc) {
		super(val, mc);
	}

	public Money(double val, MathContext mc) {
		super(val, mc);
	}

	public Money(BigInteger val, MathContext mc) {
		super(val, mc);
	}

	public Money(BigInteger unscaledVal, int scale) {
		super(unscaledVal, scale);
	}

	public Money(int val, MathContext mc) {
		super(val, mc);
	}

	public Money(long val, MathContext mc) {
		super(val, mc);
	}

	public Money(char[] in, int offset, int len) {
		super(in, offset, len);
	}

	public Money(BigInteger unscaledVal, int scale, MathContext mc) {
		super(unscaledVal, scale, mc);
	}

	public Money(char[] in, int offset, int len, MathContext mc) {
		super(in, offset, len, mc);
	}

	@Override
	public String toString() {
		return NumberFormat.getCurrencyInstance(Locale.FRANCE).format(this.doubleValue());
	}
	
	
	
}
