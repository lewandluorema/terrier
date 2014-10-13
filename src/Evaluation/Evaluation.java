package Evaluation;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import org.terrier.utility.Files;
import org.apache.log4j.Logger;
/**
 * An abstract class for evaluating the retrieval results.
 * @author Gianni Amati, Ben He, Vassilis Plachouras
 * @version $Revision: 1.25 $
 */
public abstract class Evaluation {
	protected static Logger logger = Logger.getRootLogger();
	/**
	 * A structure of a record of retrieved document.
	 */
	static public class Record {
		/** The topic number. */
		String queryNo;
		/** The rank of the document. */
		int rank;
		/** The document ID. */
		String docID;
		/** The precision at this document. */
		double precision;
		/** The recall at this document. */
		double recall;
		public Record(String queryNo, String docID, int rank) {
			this.queryNo = queryNo;
			this.rank = rank;
			this.docID = docID;
		}
	}
	
	/**
	 * Evaluates the given result file for the given qrels file.
	 * All subclasses must implement this method.
	 * @param resultFilename java.lang.String the filename of the result 
	 *        file to evaluate.
	 */
	abstract public void evaluate(String resultFilename);
	
	/**
	 * Output the evaluation result to standard output
	 */
	public void writeEvaluationResult() {
		writeEvaluationResult(new PrintWriter(new OutputStreamWriter(System.out)));
	}
	/**
	 * The abstract method that evaluates and prints
	 * the results. All the subclasses of Evaluation
	 * must implement this method.
	 * @param out java.io.PrintWriter
	 */
	abstract public void writeEvaluationResult(PrintWriter out);
	
	/**
	 * Output the evaluation result of each query to the specific file.
	 * @param evaluationResultFilename String the name of the file in which to 
	 *        save the evaluation results.
	 */
	abstract public void writeEvaluationResultOfEachQuery(String evaluationResultFilename);

	
	/**
	 * Output the evaluation result to the specific file.
	 * @param resultEvalFilename java.lang.String the filename of 
	 *        the file to output the result.
	 */
	public void writeEvaluationResult(String resultEvalFilename) {
		try {
			final PrintWriter out = new PrintWriter(Files.writeFileWriter(resultEvalFilename));
			writeEvaluationResult(out);
			out.close();
		} catch (IOException fnfe) {
			logger.fatal(
				"File not found exception occurred when trying to write to file" +resultEvalFilename,
					fnfe);
		}
	}
}
