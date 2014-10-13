/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Evaluation;

import java.util.LinkedHashMap;

/**
 *
 * @author rami
 */
public class Query implements Comparable<Object>{

    private  int queryPosition;
    private  String queryID;
    private  String queryTerm;
    private  LinkedHashMap<String,Double> queryTopic = new LinkedHashMap<String, Double>();
    public  String topic;
    public Query(){
        
    }
    public Query(int queryPosition , String queryID , String queryTerm){
        this.queryPosition = queryPosition;
        this.queryID = queryID;
        this.queryTerm = queryTerm;
    }
    public int getQueryPostion(){
        return this.queryPosition;
    }
    public String getQueryID(){
        return this.queryID;
    }
    public String getQueryTerm(){
        return this.queryTerm;
    }
    public LinkedHashMap<String,Double> getQueryTopic(){
        return this.queryTopic;
    } 
    public void setQueryPosition(int QueryP){
         this.queryPosition = QueryP;
    }
    public void setQueryID(String QueryI){
        this.queryID = QueryI;
    }
    public void setQueryTerm(String QueryT){
        this.queryTerm = QueryT;
    }
    public void setQueryTopic(LinkedHashMap<String,Double> querytopic){
        this.queryTopic = querytopic;
    }
    @Override
    public int compareTo(Object o) {
        return 0;
    }
    
    
}
