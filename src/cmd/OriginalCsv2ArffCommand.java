package cmd;

import util.Converter;

public class OriginalCsv2ArffCommand implements Command {
	public final static String ORIGINAL_CSV_DATASET = "data/mxmh_survey_results.csv";

	public final static String ORIGINAL_ARFF_DATASET = "data/mxmh_survey_results.arff";

	public void exec() {
		Converter.csv2Arff(ORIGINAL_CSV_DATASET, ORIGINAL_ARFF_DATASET);
	}

	public static void main(String args[]) {
		Command cmd = new OriginalCsv2ArffCommand();
		cmd.exec();
	}
}
