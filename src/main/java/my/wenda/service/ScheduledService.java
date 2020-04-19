package my.wenda.service;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@EnableScheduling
@Component
public class ScheduledService {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);
    /**
     * 全量索引
     */
    /*   @Scheduled(cron = "0/30 * * * * *")

     */
    //测试 每分钟更新一次
    @Scheduled(cron = "0 0/1 * * * *")
    public void fullImport() {
        System.out.println("全量更新我执行了--------------------");
        logger.info("全量更新我执行了--------------------");
        doUpdate("http://localhost:8983/solr/wenda/dataimport?command=full-import&clean=true&commit=true");
    }

    /**
     * 增量索引
     */
    //测试 每分钟更新一次
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void deltaImport() {
//        System.out.println("增量更新我执行了--------------------");
//        doUpdate("http://localhost:8983/solr/wenda/dataimport?command=delta-import&clean=false&commit=true");
//    }

    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(10);
        return scheduler;
    }


    private void doUpdate(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);
            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}