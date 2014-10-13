package uk.glasgow.cs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;
import org.terrier.utility.Rounding;
import Evaluation.AdhocEvaluation;

public class TerrierEx {
	private static String indexPath = "E:\\terrier-3.6\\terrier-3.6\\var\\index";

	public static void main(String[] args) throws IOException {
//		System.out.println("12");
		System.setProperty("terrier.home", "E:\\terrier-3.6\\terrier-3.6");
		//******************************Indexing**************************************
//		TRECIndexing MyIndex = new TRECIndexing(indexPath, "data");
		//MyIndex.index();
		//******************************Retrieval*************************************
		String QueryID = "453";
		String result = QueryIndex(QueryID, "hunger");
		writeFile(result);
		String[] results_char = result.split("\n");
		ArrayList<String> ss = new ArrayList<String>();
		int i = 0;
		for(String doc : results_char){
			String [] sss = doc.split("\t");
			String final_result = new String();
			final_result = QueryID+"\t"+sss[0].trim()+"\t"+i;
			ss.add(final_result);
			i++;
		}
		//******************************Evaluation************************************
		AdhocEvaluation Eval = new AdhocEvaluation(); 
		Eval.evaluate(ss, "E:\\terrier-3.6\\terrier-3.6\\etc\\qrels.wt10g.all");
		
	System.out.println("P@10 = "+Rounding.round(Eval.PrecAt10,4));
	System.out.println("P@100 = "+Rounding.round(Eval.PrecAt100,4));
	System.out.println("P@30 = "+Rounding.round(Eval.PrecAt30,4));
	System.out.println("Precision = "+ (double)((double)Eval.totalNumberOfRelevantRetrieved / (double) Eval.totalNumberOfRetrieved));
	System.out.println("Recalll = "+ Rounding.round((double)((double)Eval.totalNumberOfRelevantRetrieved / (double)Eval.totalNumberOfRelevant),4));

	System.out.println("TotalNumberOfRelevant = "+Eval.totalNumberOfRelevant);
	System.out.println("TotalNumberOfRelevantRetrieved = "+Eval.totalNumberOfRelevantRetrieved);
	System.out.println("TotalNumberOfRetrieved = "+Eval.totalNumberOfRetrieved);
	System.out.println("Average Precision = "+Eval.EvaluationResult());
	org.terrier.utility.Rounding.round(1.2555555,4);
		
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
		srq.addMatchingModel("Matching", "BM25");
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

	public static void writeFile(String string) throws IOException {

		File txt = new File("E:\\terrier-3.6\\terrier-3.6\\var\\results\\result.res");
		if (!txt.exists()) {
			txt.createNewFile();
		}
	        FileWriter writer;
	        try {
	            writer = new FileWriter("E:\\terrier-3.6\\terrier-3.6\\var\\results\\result.res");
	            writer.write(string);
	            writer.flush();
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
}
