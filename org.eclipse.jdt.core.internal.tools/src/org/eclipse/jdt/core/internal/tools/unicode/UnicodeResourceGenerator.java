package org.eclipse.jdt.core.internal.tools.unicode;

import java.io.IOException;

public class UnicodeResourceGenerator {

	double unicodeValue = -1.0;
	String[] args = null;

	UnicodeResourceGenerator(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: " + GenerateIdentifierStartResources.class + " <unicode version> <path to ucd.all.flat.xml> <export directory>"); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}

		this.unicodeValue = 0.0;
		try {
			this.unicodeValue = Double.parseDouble(args[0]);
		} catch (NumberFormatException e) {
			System.err.println("<unicode version> has the wrong format. Expecting a double value. e.g. 8.0"); //$NON-NLS-1$
			return;
		}
		this.args = args;
	}

	public void generate(Environment environment) throws IOException {
		if (this.args == null) {
			// wrong settings
			return;
		}
		String[] codePointTable = TableBuilder.buildTables(this.unicodeValue, true, environment, this.args[1]);
		if (codePointTable == null) {
			System.err.println("Generation failed"); //$NON-NLS-1$
			return;
		}
		Integer[] codePoints = CodePointsBuilder.build(codePointTable, environment);
		if (codePoints == null) {
			System.err.println("Generation failed"); //$NON-NLS-1$
			return;
		}
		FileEncoder.encodeResourceFiles(codePoints, environment, this.args[2]);
	}
}