package my.wenda.service;

import my.wenda.model.Question;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService
{
    private static final String solr_url = "http://localhost:8983/solr/wenda";
    private static HttpSolrClient client = new HttpSolrClient.Builder(solr_url).build();
    private static final String question_title_field = "question_title";
    private static final String question_content_field = "question_content";

    public List<Question> searQuestion(String keyword, int offset, int count, String hlPre, String hlPos) throws IOException, SolrServerException {
        List<Question> questionList = new ArrayList<>();
        keyword = "question_title:"+keyword+"\nquestion_content:"+keyword;
        SolrQuery query = new SolrQuery(keyword);
        if(offset>=2)
            offset= (offset-1)*count;   //传进来的offse是页数，转换成偏移量,第一次访问offset为0，第二次为2
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl",question_title_field+","+question_content_field);
        QueryResponse response = client.query(query);//偏移量
        for(Map.Entry<String, Map<String, List<String>>> entry:response.getHighlighting().entrySet()){
            Question q = new Question();
            q.setId(Integer.parseInt(entry.getKey()));
            if(entry.getValue().containsKey(question_content_field)){
                List<String> contentList = entry.getValue().get(question_content_field);
                if(contentList.size()>0){
                    q.setContent(contentList.get(0));
                }
            }
            if(entry.getValue().containsKey(question_title_field)){
                List<String> titleList = entry.getValue().get(question_title_field);
                if(titleList.size()>0){
                    q.setContent(titleList.get(0));
                }
            }
            questionList.add(q);
        }

        return questionList;
    }

    public boolean indexQuestion(int qid, String title, String content) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id",qid);
        doc.setField(question_title_field, title);
        doc.setField(question_content_field, content);
        UpdateResponse response = client.add(doc, 1000);

        return response!=null && response.getStatus() == 0;
    }

}
