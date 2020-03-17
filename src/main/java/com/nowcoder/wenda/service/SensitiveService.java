package com.nowcoder.wenda.service;

import org.apache.commons.lang3.CharUtils;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            //D:\Code_new\wenda1\src\main\resources\SensitiveWords.txt
            File file = new File(this.getClass().getResource("/SensitiveWords.txt").getPath());
            InputStream is = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while((lineTxt = bufferedReader.readLine())!=null){
                addWord(lineTxt.trim());
            }
            read.close();
        }catch(Exception e){
            logger.error("读取敏感词文件失败  "+e.getMessage());
        }
    }

    //增加关键词
    private void addWord(String lineTxt){
        TreeNode tempNode = rootNode;
        for(int i=0;i<lineTxt.length(); ++i){
            Character c = lineTxt.charAt(i);
            if(isSymbol(c)) //添加时去除多余字符
                continue;

            TreeNode node = tempNode.getSubNode(c);

            if(node == null){
                node = new TreeNode();
                tempNode.addSubNode(c, node);
            }

            tempNode = node;

            if(i == lineTxt.length() -1){
                tempNode.setkeywordEnd(true);
            }
        }
    }

    private  class TreeNode{
        //是不是关键词的结尾
        private boolean end = false;

        //当前节点,及节点下的s子节点
        private Map<Character, TreeNode> subNodes = new HashMap<Character, TreeNode>();

        public void addSubNode(Character key, TreeNode node){
            subNodes.put(key, node);
        }

        TreeNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeyWordEnd(){ return end; }
        void setkeywordEnd(boolean end) {
            this.end = end;
        }
    }

    private  TreeNode rootNode = new TreeNode();

    //判断是否为有效字符，用以去除空格或符号间隔 （色 情）避免敏感词过滤逃逸
    private boolean isSymbol(char c){
        int ic = (int) c;
        //东亚文字 0x2E80-0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String  filter(String text){     //调用敏感词过滤
        if(StringUtils.isBlank(text)){
            return text;
        }

        StringBuilder result = new StringBuilder();

        String replacement ="***";
        TreeNode tempNode = rootNode;

        int begin =0;
        int position = 0;

        while(position < text.length()){
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getSubNode(c);  //子节点
            if(tempNode ==null){
                result.append((text.charAt(begin)));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if(tempNode.isKeyWordEnd()){
                //发现敏感词
                result.append(replacement);
                position = position +1;
                begin = position;
                tempNode = rootNode;
            }else{
                ++position;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

//    public static void main(String[] args) throws Exception {
//        SensitiveService s = new SensitiveService();
//        s.afterPropertiesSet();
//        System.out.println(s.filter("hi嫖娼  你好色—— 情"));
//    }
}
