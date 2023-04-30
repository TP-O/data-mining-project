package cmd;

import util.Loader;
import util.Saver;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.InterquartileRange;
import weka.filters.unsupervised.instance.RemoveWithValues;

public class RemoveOutlierAndExtremeDataCommand implements Command {
	public final static String MARKED_CSV_DATASET = "data/marked_mxmh_survey_results.csv";

	public final static String MARKED_ARFF_DATASET = "data/marked_missing_mxmh_survey_results.arff";

	public final static String REMOVED_EXTREME_CSV_DATASET = "data/removed_extreme_mxmh_survey_results.csv";

	public final static String REMOVED_EXTREME_ARFF_DATASET = "data/removed_extreme_mxmh_survey_results.arff";

	public final static String REMOVED_OUTLIER_CSV_DATASET = "data/removed_outlier_mxmh_survey_results.csv";

	public final static String REMOVED_OUTLIER_ARFF_DATASET = "data/removed_outlier_mxmh_survey_results.arff";

	public void exec() {
		Instances dataset = Loader.loadArff(HandleMissingDataCommand.HANDLE_MISSING_ARFF_DATASET);

		try {
			InterquartileRange filter = new InterquartileRange();
			filter.setOptions(new String[] { "-R", "first-last", "-O", "3.0", "-E", "6.0"
			});
			filter.setInputFormat(dataset);
			Instances markedData = Filter.useFilter(dataset, filter);

			Saver.saveArff(MARKED_ARFF_DATASET, markedData);
			Saver.saveCsv(MARKED_CSV_DATASET, markedData);

			// Remove extreme data
			RemoveWithValues removeFilter = new RemoveWithValues();
			removeFilter.setOptions(new String[] { "-S", "0.0", "-C", "33", "-L", "last"
			});
			removeFilter.setInputFormat(markedData);
			Instances removedExtremeData = Filter.useFilter(markedData, removeFilter);

			Saver.saveArff(REMOVED_EXTREME_ARFF_DATASET, removedExtremeData);
			Saver.saveCsv(REMOVED_EXTREME_CSV_DATASET, removedExtremeData);

			// Remove outlier data
			removeFilter.setOptions(new String[] { "-S", "0.0", "-C", "32", "-L", "last"
			});
			removeFilter.setInputFormat(removedExtremeData);
			Instances removedOutlierData = Filter.useFilter(removedExtremeData,
					removeFilter);

			Saver.saveArff(REMOVED_OUTLIER_ARFF_DATASET, removedOutlierData);
			Saver.saveCsv(REMOVED_OUTLIER_CSV_DATASET, removedOutlierData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Command cmd = new RemoveOutlierAndExtremeDataCommand();
		cmd.exec();
	}
}
