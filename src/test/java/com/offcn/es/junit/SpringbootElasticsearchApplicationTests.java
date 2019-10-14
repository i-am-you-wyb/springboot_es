package com.offcn.es.junit;


import com.offcn.es.SpringbootElasticsearchApplication;
import com.offcn.es.bean.EsEntity;
import com.offcn.es.bean.EsPage;
import com.offcn.es.bean.User;
import com.offcn.es.util.ElasticsearchUtil;
import org.elasticsearch.common.UUIDs;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootElasticsearchApplication.class)
public class SpringbootElasticsearchApplicationTests {
    @Autowired
public ElasticsearchUtil<User> util;
    /**
     * 使用分词查询  高亮 排序 ,并分页
     *
     * @param index          索引名称
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return 结果
     */
    @Test
    public void searchDataPage() {
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "独孤");
        EsPage esPage = util.searchDataPage("userindex", 1, 2, queryBuilder, "id,name,address,sex", "name");
        System.out.println("**************************************");
        System.out.println(esPage);
        System.out.println("**************************************");
    }
    /**
     * 批量删除
     */
    @Test
    public void deleteBatch(){
        List list=new ArrayList();
        list.add("r8cyyW0Bs0DogAPtiMdG");
        //list.add(100);
        util.deleteBatch("userindex",list);

    }
    /**
     * 删除数据
     */
    @Test
    public void deleteByQuery(){

        util.deleteByQuery("userindex",new TermQueryBuilder("id", 5));

    }
    /**
     * 添加数据
     */
    @Test
    public void addData() throws IOException {
        //创建json文档内容构建器对象
        XContentBuilder content = XContentFactory.jsonBuilder();
        //封装数据
        content.startObject()
                .field("id", "100")
                .field("name", "scott")
                .field("address","北京")
                .field("sex", 100).endObject();
        //
        util.addData(content,"userindex", UUIDs.base64UUID());
    }
    /**
     * 创建索引
     */
    @Test
    public  void createIndex() {
        System.out.println(util.createIndex("userindex"));
    }
    @Test
    public void insertBatch() {
        //文档数据
        User user=new User();
        user.setId(5);
        user.setAddress("北京");
        user.setName("独孤想败");
        user.setSex(1);
        //封装文档数据到EsEntity对象中
        EsEntity es01=new EsEntity();
        es01.setId(UUIDs.base64UUID());
        es01.setData(user);

        User user2=new User();
        user2.setId(8);
        user2.setAddress("北京");
        user2.setName("独孤求败");
        user2.setSex(0);
        EsEntity es02=new EsEntity();
        es02.setId(UUIDs.base64UUID());
        es02.setData(user2);


        User user3=new User();
        user3.setId(9);
        user3.setAddress("北京");
        user3.setName("不得不败");
        user3.setSex(1);
        EsEntity es03=new EsEntity();
        es03.setId(UUIDs.base64UUID());
        es03.setData(user3);


        List<EsEntity> list =new ArrayList<EsEntity>();
        list.add(es01);
        list.add(es02);
        list.add(es03);


        util.insertBatch("userindex",list);
    }

    /**
     * 测试索引是否存在
     */
    @Test
    public  void isIndexExist() {
        System.out.println(util.isIndexExist("userindex"));
    }
}

