package udacity.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A bolt that counts the words that it receives
 */
public class CountStream extends BaseRichBolt
{

  private HashMap<String, Integer> countries;
  private int[][] cnt;
  private String[][] words;
  private int topN = 5;
  private int lastIndex;

  // To output tuples from this bolt to the next stage bolts, if any
  private OutputCollector collector;

  // Map to store the count of the words
  private Map<String, Long> countMap;

  public CountStream(int topN){
  }

  @Override
  public void prepare(
      Map                     map,
      TopologyContext         topologyContext,
      OutputCollector         outputCollector)
  {

    // save the collector for emitting tuples
    collector = outputCollector;
    words = new String[250][topN];
    cnt = new int[250][topN];
    countries =  new HashMap<String, Integer>();
    lastIndex = 0;
  }

  @Override
  public void execute(Tuple tuple)
  {
    String countryName = tuple.getStringByField("country");
    String hashtag = tuple.getStringByField("word");
    boolean found = false;
      if(countries.containsKey(countryName)){
        int index = countries.get(countryName);
        for(int i = 0 ;i < topN ; i++){
          if(words[index][i] == hashtag){
            cnt[index][i]++;
            found = true;
          }
        }
        if(!found){
          for(int i = 0 ;i < topN; i++){
            if(cnt[index][i] == 0){
              cnt[index][i] = 1;
              words[index][i] = hashtag;
              found = true;
              break;
            }
          }
          if(!found){
            for(int i = 0 ;i < topN; i++){
              cnt[index][i]--;
            }
          }
        }
        collector.emit(new Values(countryName, getHashtags(index)));
      }else{
        countries.put(countryName, lastIndex);
        words[lastIndex][0] = hashtag;
        cnt[lastIndex][0]++;
        lastIndex++;
        collector.emit(new Values(countryName, hashtag));
      }

    // emit the word and count
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
  {
    // tell storm the schema of the output tuple for this spout
    // tuple consists of a two columns called 'word' and 'count'

    // declare the first column 'word', second column 'count'
    outputFieldsDeclarer.declare(new Fields("country","hashtags"));
  }

  private String getHashtags(int index){
    String str = "";
    for(int i = 0 ;i < topN; i++){
      if(words[index][i] == null){
        break;
      }
      str = str + "," + words[index][i];
    }
    return str;
  }
}
