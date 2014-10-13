package TerrierTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.terrier.applications.TRECIndexing;
import org.terrier.evaluation.AdhocEvaluation;
import org.terrier.evaluation.AdhocFullQueryEvaluation;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;

public class TerrierUse {
	private static String indexPath = "C:\\terrier-3.6\\terrier-3.6\\var\\index";

	public static void main(String[] args) throws IOException {
		System.out.println("dafaf");
		System.setProperty("terrier.home", "C:\\terrier-3.6\\terrier-3.6");
//		 TRECIndexing MyIndex = new TRECIndexing(indexPath, "data");
//		 MyIndex.index();
		// 451:what is a bengals cat
		// QueryIndex("451", "what is a bengals cat");

		String s = QueryIndex("455", "whan did jackie robinson appear at his first game456");
		String[] results_char = s.split(" ");
		writeFile(s);
		// System.out.println(s);

		// AdhocEvaluation Eval = new AdhocEvaluation();
		// Eval.evaluate(sb,
		// "/media/rami/PhD Work/PhD Workplace/terrier-3.6/etc/qrels.trec9.main_web");
		// ******************************Evaluation************************************
		// AdhocFullQueryEvaluation eval = new AdhocFullQueryEvaluation();
		// eval.evaluate("/home/node1/Desktop/qrels.wt10g.all","/home/node1/Desktop/Results");
		// AdhocEvaluation Eval = new AdhocEvaluation();
		// Eval.writeEvaluationResult("C:\\terrier-3.6\\terrier-3.6\\var\\results\result.txt");
		// Eval.evaluate(s);

	}

	public static void writeFile(String string) throws IOException {

		File txt = new File("C:\\terrier-3.6\\terrier-3.6\\var\\results\\result.res");
//		File txt = new File("C:\\terrier-3.6\\terrier-3.6\\var\\results\result.txt");
		if (!txt.exists()) {
			txt.createNewFile();
		}
//		byte bytes[] = new byte[512];
//		int b = Integer.parseInt(string);
//		FileOutputStream fos = new FileOutputStream(txt);
//		fos.write(bytes, 0, b);
//		fos.close();
		
	        FileWriter writer;
	        try {
	            writer = new FileWriter("C:\\terrier-3.6\\terrier-3.6\\var\\results\\result.res");
	            writer.write(string);
	            writer.flush();
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	public static String QueryIndex(String queryId, String query) {

		// String queryId refer to query number
		// String query refere to query terms
		// ******************************************************************************
		StringBuilder sbuffer = new StringBuilder(); // string for store the
														// retrieval results
		Index index = Index.createIndex(indexPath, "data");
		int RESULTS_LENGTH = 0; // Number of retrieved documents
		// ***************************************************************************************
		// Please have a look on the terrier documentation about that.
		Manager m = new Manager(index);
		SearchRequest srq = m.newSearchRequest(queryId, query);
		// srq.addMatchingModel("Matching", method);
		srq.addMatchingModel("Matching", "DFR_BM25");
		m.runPreProcessing(srq);
		m.runMatching(srq);
		final ResultSet set = srq.getResultSet();
		final String metaIndexDocumentKey = "docno";
		final int[] docids = set.getDocids();
		final double[] scores = set.getScores();
		String[] docnos = null;
		if (set.hasMetaItems(metaIndexDocumentKey)) {
			docnos = set.getMetaItems(metaIndexDocumentKey);
		} else {
			final MetaIndex metaIndex = index.getMetaIndex();
			try {
				docnos = metaIndex.getItems(metaIndexDocumentKey, docids);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		final int maximum = RESULTS_LENGTH > set.getResultSize()
				|| RESULTS_LENGTH == 0 ? set.getResultSize() : RESULTS_LENGTH;// protected
																				// static
																				// int
																				// RESULTS_LENGTH
																				// =
																				// 1000;
		for (int i = 0; i < maximum; i++) {
			if (scores[i] <= 0d) {
				continue;
			}
			sbuffer.append(docnos[i]);
			sbuffer.append("\t");
			sbuffer.append(scores[i]);
			sbuffer.append("\n");
		}
		try {
			index.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sbuffer.toString();
	}

}
