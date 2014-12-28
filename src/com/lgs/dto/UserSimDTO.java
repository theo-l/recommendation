/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lgs.dto;


import com.lgs.bean.UserTagBook;
import com.lgs.bean.Vector;
import com.lgs.dao.CollaborativeUBDao;
import com.lgs.dao.CollaborativeUTDao;
import com.lgs.dao.MacroUserBookDao;
import com.lgs.dao.MacroUserTagDao;
import com.lgs.dao.UserDao;
import com.lgs.dao.UserMacroBookSimDao;
import com.lgs.dao.UserMacroTagSimDao;
import com.lgs.dao.UserSimDao;
import com.lgs.dao.UserTagBookDao;
import com.lgs.dao.UserTagDao;
import com.lgs.similar.CosinSimilar;
import com.lgs.similar.DiceSimilar;
import com.lgs.similar.JaccardSimilar;
import com.lgs.similar.MatchSimilar;
import com.lgs.similar.Method;
import com.lgs.similar.OverlapSimilar;
import com.lgs.similar.Type;
import com.lgs.similar.VectorSimilar;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author acer
 * 处理用户相似性的操作对象
 */
public class UserSimDTO {
    private UserTagDao utDao;
    private UserDao uDao;
    private List<Integer> allUsers;//所有参与标注行为的用户id数列
    private VectorSimilar similar;
    private int method;//根据其值来确定具体的相似性计算方法
    private int type;//根据其值来确定具体的降维策略
    private UserTagBookDao utbDao;
    private MacroUserTagDao mutDao;//用来访问每个用户的标签的局部权值
    private MacroUserBookDao mubDao;
    private UserMacroTagSimDao umtsDao;
    private UserMacroBookSimDao umbsDao;
    private CollaborativeUBDao cubDao;
    private CollaborativeUTDao cutDao;
    private UserSimDao usDao;
    
       public static void main(String[] args) throws IOException{
        UserSimDTO usDto=new UserSimDTO();
        usDto.setMethod(Method.Cosine);
        usDto.setType(Type.Project);
        usDto.config();     
//        usDto.generateUserSimMatrix();
//        usDto.generateUserMacroSimMatrix();
//        usDto.parseUserTaggingToMacro();
        usDto.parseUserTaggingToCollaborative();
    }
 
    
    //将所有的用户标注行为，按照每个用户进行分解，得到关于每个用户的标签或书籍的局部权值，进行存储，以便在进行局部相似性计算
    public void parseUserTaggingToMacro(){
        utbDao=new UserTagBookDao();
        mutDao=new MacroUserTagDao();//用来访问宏观策略下的用户的局部标签权值
        mubDao=new MacroUserBookDao();//用来访问宏观策略下的用户的局部书籍权值
        //在user_tag_book表中 获得所有参与标注的用户的id 存入到allUsers
        int  size=this.getAllUsers().size();
        //按照每个用户的id获取其所有的标注行为 utbDao
        for(int i=0;i<size;i++){
            
          //userBooks,userTags分别存储用户标注的数目数和用户使用的标签数
            int userBooks=0,userTags=0;
            
            List<UserTagBook> taggings=utbDao.getTaggingByUid(this.getAllUsers().get(i));
            
            
      
            
             //对每个用户的标注行为进行遍历，遍历时需要存储每个标签标注的数目数，以及每个书目被标注的标签数 采用HashMap<Integer,HashSet>进行存储
           HashMap<Integer,HashSet<Integer>> tags=new HashMap<Integer,HashSet<Integer>>();
           HashMap<Integer,HashSet<Integer>> books=new HashMap<Integer,HashSet<Integer>>();
           int uid=0;
           for(UserTagBook utb:taggings){
               uid=utb.getUid();
               int tid=utb.getTid();
               int bid=utb.getBid();
               
               //测试标签是否已经存在
               if(tags.containsKey(tid)){
                   //标签如果已经存在，则取出相应的标签的标注向量，以书籍id作为元素
                   
                   HashSet<Integer> tag=tags.get(tid);
                   
                   //测试书籍是否已经存在于标签向量中，如果不存在则加入到标签向量中
                   if(!tag.contains(bid)){
                       tag.add(bid);
                   }
               }else{
                   HashSet<Integer> tag=new HashSet<Integer>();
                   tag.add(bid);
                   tags.put(tid, tag);
               }
               
               //测试相应的书籍id是否已经存在
               if(books.containsKey(bid)){
                   HashSet<Integer> book=books.get(bid);
                   
                   //测试标签是否已经在书籍的向量中,如果不存在则加入到书籍向量中，如果存在则忽略
                   if(!book.contains(tid)){
                       book.add(tid);
                   }
               }else{
                   HashSet<Integer> book=new HashSet<Integer>();
                   book.add(tid);
                   
                   books.put(bid, book);
               }
          
            
       
        
        //遍历完成之后，计算每个标签或书籍的权值 并进行存储
         }
           userTags=tags.keySet().size();
           userBooks=books.keySet().size();
           
           System.out.println("user's id is: "+uid);
                 
           
           System.out.println("user's tag set");
           for(Integer key:tags.keySet()){
               double value=(double)(tags.get(key).size())/userBooks;
               mutDao.saveMacroUserTag(uid, key, value);
               System.out.printf("tagid %d value is: %.5f  ",key,value);
           }
           System.out.println();
           
           System.out.println("user's book set");
           for(Integer key:books.keySet()){
               double value=(double)(books.get(key).size())/userTags;
               mubDao.saveMacroUserBook(uid, key, value);
               System.out.printf("bookid %d value is: %.5f  ",key,value);
           }
           System.out.println();
        //直到结束所有用户遍历结束
        }
        
        mutDao.closeStatement();
        mubDao.closeStatement();
        
        
    }
    
    //将所有的用户标注行为，按照协作方式进行分解得到类似于Macro形式的局部权值，以便在利用协同过滤方式进行局部相似性时计算
    public void parseUserTaggingToCollaborative(){
          utbDao=new UserTagBookDao();
          cubDao=new CollaborativeUBDao();
          cutDao=new CollaborativeUTDao();
        //在user_tag_book表中 获得所有参与标注的用户的id 存入到allUsers
        int  size=this.getAllUsers().size();
        Integer[] allUser=new Integer[size];
        this.getAllUsers().toArray(allUser);
        this.getAllUsers().clear();
        
/*
 * 
 */
//        for(Integer i:allUser){
//        System.out.print(i+"  ");
//        }
//        
//        System.out.println("所有的用户长度 ："+allUser.length);
//        System.out.println();
//        if(true)
//            return;
//        size=10;
        //按照每个用户的id获取其所有的标注行为 utbDao
        /*
         * 
         */
        for(int i=0;i<size;i++){
            
          //userBooks,userTags分别存储用户标注的数目数和用户使用的标签数
            int userBooks=0,userTags=0;
            
            List<UserTagBook> taggings=utbDao.getTaggingByUid(allUser[i]);
            
            
      
            
             //对每个用户的标注行为进行遍历，遍历时需要存储每个标签标注的数目数，以及每个书目被标注的标签数 采用HashMap<Integer,HashSet>进行存储
           HashMap<Integer,HashSet<Integer>> tags=new HashMap<Integer,HashSet<Integer>>();
           HashMap<Integer,HashSet<Integer>> books=new HashMap<Integer,HashSet<Integer>>();
           int uid=0;
           for(UserTagBook utb:taggings){
               uid=utb.getUid();
               int tid=utb.getTid();
               int bid=utb.getBid();
               
               //测试标签是否已经存在
               if(tags.containsKey(tid)){
                   //标签如果已经存在，则取出相应的标签的标注向量，以书籍id作为元素
                   
                   HashSet<Integer> tag=tags.get(tid);
                   
                   //测试书籍是否已经存在于标签向量中，如果不存在则加入到标签向量中
                   if(!tag.contains(bid)){
                       tag.add(bid);
                   }
               }else{
                   HashSet<Integer> tag=new HashSet<Integer>();
                   tag.add(bid);
                   tags.put(tid, tag);
               }
               
               //测试相应的书籍id是否已经存在
               if(books.containsKey(bid)){
                   HashSet<Integer> book=books.get(bid);
                   
                   //测试标签是否已经在书籍的向量中,如果不存在则加入到书籍向量中，如果存在则忽略
                   if(!book.contains(tid)){
                       book.add(tid);
                   }
               }else{
                   HashSet<Integer> book=new HashSet<Integer>();
                   book.add(tid);
                   
                   books.put(bid, book);
               }
          
            
       
        
        //遍历完成之后，计算每个标签或书籍的权值 并进行存储
         }
           userTags=tags.keySet().size();
           userBooks=books.keySet().size();
           
           System.out.println("user's id is: "+uid);
                 
           
           System.out.println("user's tag set");
           for(Integer key:tags.keySet()){
               double value=(double)(tags.get(key).size())/(userBooks+1);
               cutDao.saveCollaborativeUT(uid, key, value);
               tags.get(key).clear();
               System.out.printf("tagid %d value is: %.5f  ",key,value);
           }
           tags.clear();
           
           System.out.println();
           
           System.out.println("user's book set");
           for(Integer key:books.keySet()){
               double value=(double)(books.get(key).size())/(userTags+1);
               cubDao.saveCollaborativeUB(uid, key, value);
               books.get(key).clear();
               System.out.printf("bookid %d value is: %.5f  ",key,value);
           }
           books.clear();
           System.out.println();
        //直到结束所有用户遍历结束
        }
    }
    
    public UserSimDTO(){
       //初始化用户-标签数据访问对象
        setUtDao(new UserTagDao());
        
        //初始化用户数据访问对象
        setuDao(new UserDao());
               
        //初始化参与标注行为的所有用户的id集合
        setAllUsers(this.uDao.getAllUsersCount());      
    }
    
    public void setType(int type){
        this.type=type;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void config(){
        
        //根据设置的相应的相似性计算方法类型，确定具体的相似性计算方法
        switch(this.method){
            case Method.Cosine:this.setSimilar(new CosinSimilar());break;
            case Method.Dice:this.setSimilar(new DiceSimilar());break;
            case Method.Jaccard:this.setSimilar(new JaccardSimilar());break;
            case Method.Match:this.setSimilar(new MatchSimilar()); break;
            case Method.Overlap:this.setSimilar(new OverlapSimilar());break;
            default:break;
        }
        
        this.similar.setType(type);
        this.usDao=new UserSimDao(this.getMethod(),this.getType());
        
    }
    
    public void setMethod(int method){
        this.method=method;
    }
    
    public int getMethod(){
        return this.method;
    }
            
    //生成宏观的降维的相似矩阵
    public void generateUserMacroSimMatrix(){
        utbDao=new UserTagBookDao();
        mutDao=new MacroUserTagDao();
        mubDao=new MacroUserBookDao();
        umtsDao=new UserMacroTagSimDao();
        umbsDao=new UserMacroBookSimDao();
//        int size=this.getAllUsers().size();
        int size=this.getAllUsers().size();
        
        for(int i=0;i<size;i++){
            this.parseUserTagging( utbDao.getTaggingByUid(this.getAllUsers().get(i)));
        }
        
    }
    
    public boolean isVectorIn(ArrayList<Vector> list,int key){
        boolean flag=false;
        if(list.size()<1){
            return flag;
        }else{
        for(Vector v:list){
            if(v.getVid()==key){
                flag=true;
            }
        }
        
        return flag;
        }
    }
    
    public Vector getVector(ArrayList<Vector> list,int key){
        Vector vector=null;
        for(Vector v:list){
            if(v.getVid()==key){
                vector=v;
            }
        }
        return vector;
    }
    
    //将每个用户的标注关系进行解析
    //得到用户的每个标签的向量
    //得到用户的每个书籍的向量
    /*
     * 生成宏观或协同的用户标签或书籍的相似矩阵
     * 将所有的用户的标注按照用户的局部性进行处理，并将相应的结果存入到数据库中
     */           
    public void parseUserTagging(List<UserTagBook> taggings){
        
//        HashSet tags=new HashSet();
//        HashSet books=new HashSet();
        
//        HashMap<Integer,HashSet<Integer>> tags=new HashMap<Integer,HashSet<Integer>>();
//        HashMap<Integer,HashSet<Integer>> books=new HashMap<Integer,HashSet<Integer>>();
        ArrayList<Vector> tags=new ArrayList<Vector>();
        ArrayList<Vector> books=new ArrayList<Vector>();
        //用户的id值
        int uid=0;
        
        for(UserTagBook utb:taggings){
            uid=utb.getUid();
            int tagid=utb.getTid();
            int bookid=utb.getBid();
            
              //其中的一个标签向量
               Vector tag=null;
            //如果用户的标签中包含了当前的标签id,则取出当前id对应的标签向量
            if(this.isVectorIn(tags, tagid)){
                tag=this.getVector(tags, tagid);
                //取得标签的向量之后，查看相应的bookid是否已经在标签向量中存在，存在则忽略不计，否则添加到标签向量中
                if(!tag.getElements().containsKey(bookid)){
                    tag.getElements().put(bookid, mubDao.getValueByUidBid(uid, bookid));//获取相应用户的局部书籍的权值，并赋值给标签向量中书籍元素
                }
            }else{//如果标签向量不存在，则创建新的标签向量，将对应的Bookid添加到对应的标签向量中
                tag=new Vector();
                tag.setVid(tagid);
                tag.addElement(bookid, mubDao.getValueByUidBid(uid, bookid));//获取相应用户的局部书籍的权值，并赋值给标签向量中书籍元素
                
                tags.add(tag);
            }
            
            //用户其中的一个书籍向量
            Vector book=null;
            //查看是否已经建立了用户的书籍向量，如果存在取出相应的bookid对应的向量
            if(this.isVectorIn(books, bookid)){
                book=this.getVector(books, bookid);
                //取得书籍向量之后，查看相应的tagid是否已经在书籍向量中存在，存在则忽略不计，否则添加到书籍向量中
                if(!book.getElements().containsKey(tagid)){
                    book.addElement(tagid, mutDao.getValueByUidTid(uid, tagid));//取得相应的标签在用户局部性的权值，并赋值给向量元素
                    
                }
            }else{
              book=new Vector();
              book.setVid(bookid);
              book.addElement(tagid, mutDao.getValueByUidTid(uid, tagid));//取得相应的标签在用户局部性的权值，并赋值给向量元素
              
             books.add(book);
            }
            
            
            
//            System.out.printf("userid is %d tagid is %d bookid is %d%n",userid,tagid,bookid);
        }
        
        
        //生成用户的标签以及书籍的向量完成，然后对向量进行相似性计算
        //首先对用户的标签向量进行标签的相似性计算
        
        System.out.println("userid is : "+uid);
        int size=tags.size();
        for(int i=0;i<size-1;i++){
            Vector tag1=tags.get(i);
            
            for(int j=i+1;j<=size-1;j++){
                Vector tag2=tags.get(j);
                
                double sim=this.getSimilar().getSimilar(tag1, tag2);
                if(sim>0){
                System.out.printf("tag1's id %d :tag2's id %d  Sim is %.5f%n",tag1.getVid(),tag2.getVid(),sim);
                umtsDao.saveUserMacroTagSim(uid, tag1.getVid(), tag2.getVid(), BigDecimal.valueOf(sim));
                }
            }
            
        }
        
        size=books.size();
        for(int i=0;i<size-1;i++){
            Vector book1=books.get(i);
            
            for(int j=i+1;j<=size-1;j++){
                Vector book2=books.get(j);
                
                double sim=this.getSimilar().getSimilar(book1, book2);
                if(sim>0){
                System.out.printf("book1's id %d:book2's id %d Sim is %.5f%n",book1.getVid(),book2.getVid(),sim);
                umbsDao.saveUserMacroBookSim(uid, book1.getVid(), book2.getVid(), BigDecimal.valueOf(sim));
                }
            }
        }
        
//        System.out.println("userid is:"+uid);
//        //打印用户的所有标签向量
//        for(Vector tag:tags){
//            System.out.println("tag id is "+tag.getVid()+"--");
//            
//            for(Integer bookid:tag.getElements().keySet()){
//                System.out.printf("bookid:%d-value:%.2f  ", bookid,tag.getElements().get(bookid));
//            }
//            System.out.println();
//            
//        }
        
        //打印用户的所有书籍向量
//        for(Vector book:books){
//            System.out.printf("book id is: %d---%n", book.getVid());
//            
//            for(Integer tagid:book.getElements().keySet()){
//                System.out.printf("tagid:%d-value:%.2f  ", tagid,book.getElements().get(tagid));
//            }
//            System.out.println();
//        }
        
    }
    
    
    //用来根据不同的相似性计算方法来生成一般直接投影的用户相似矩阵
    public void generateUserSimMatrix() throws  IOException{
        
        //设置相似性计算法的降维策略，Project、Distribution、Macro、Collaborative
      
        String file="userSim.txt";
        
        
        BufferedWriter bw=new BufferedWriter(new FileWriter(file));
        int test=10;
        
        
        List<Vector> users=new ArrayList<Vector>();
        //在真是实验时，test的值设置为allUsers的长度
        for(int i=0;i<test;i++){
          Vector v=this.utDao.getProjectUserProfileByUid(this.getAllUsers().get(i));
          System.out.println(this.getAllUsers().get(i));
          users.add(v);
        }
        
        for(int i=0;i<test-1;i++){
            Vector v1=users.get(i);
            
            for(int j=i+1;j<=test-1;j++){
                
                Vector v2=users.get(j);
                
                //此处的相似性计算方法在对象配置阶段的config进行设置
                double sim=this.getSimilar().getSimilar(v1, v2);
             
                System.out.println(v1);
                System.out.println(v2);
                System.out.printf("tag %d and tag %d  : similar %.5f%n",v1.getVid(),v2.getVid(),sim);
               
                if(sim>0){
                    bw.write("tag "+v1.getVid()+" tag "+v2.getVid()+" :"+"similar "+sim+"\n");
                }
                
                
            }
        }
        bw.close();
        
    }

    /**
     * @return the utDao
     */
    public UserTagDao getUtDao() {
        return utDao;
    }

    /**
     * @param utDao the utDao to set
     */
    public void setUtDao(UserTagDao utDao) {
        this.utDao = utDao;
    }

    /**
     * @return the uDao
     */
    public UserDao getuDao() {
        return uDao;
    }

    /**
     * @param uDao the uDao to set
     */
    public void setuDao(UserDao uDao) {
        this.uDao = uDao;
    }

    /**
     * @return the allUsers
     */
    public List<Integer> getAllUsers() {
        return allUsers;
    }

    /**
     * @param allUsers the allUsers to set
     */
    public void setAllUsers(List<Integer> allUsers) {
        this.allUsers = allUsers;
    }

    /**
     * @return the similar
     */
    public VectorSimilar getSimilar() {
        return similar;
    }

    /**
     * @param similar the similar to set
     */
    public void setSimilar(VectorSimilar similar) {
        
        this.similar = similar;
    }
    
}
