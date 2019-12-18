package com.aaa.until;

import io.netty.util.internal.StringUtil;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

import static com.aaa.until.UserStatic.*;
import static com.aaa.until.StatusEnum.*;

/*  @  时间    :  2019/12/18 15:15:17
 *  @  类名    :  EsService
 *  @  创建人  :  Xie
 *  @  描述    :
 *
 */
@Component
public class EsService {
    @Autowired
    private TransportClient transportClient;

    private static TransportClient client;
    @PostConstruct
    public void init(){
        client =this.transportClient;
    }
    public static Map<String,Object> rest = new HashMap<String,Object>();

    /**
     * 先判断索引是否存在
     * 在判断操作是否成功
     * @param index
     * @return
     */
    public static Map<String,Object> createIndex(String index){
        //判断
        if(indexExist(index)){
            rest.put(CODE, EXIST.getCode());
            rest.put(MSG,EXIST.getMsg());
        }else {
            CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate(index).execute().actionGet();
            if(createIndexResponse.isAcknowledged()){
                rest.put(CODE,OPRATION_SUCCESS.getCode());
                rest.put(MSG,OPRATION_SUCCESS.getMsg());
            }else {
                rest.put(CODE,OPRATION_FATLED.getCode());
                rest.put(MSG,OPRATION_FATLED.getMsg());
            }
        }

        return rest;
    }

    /**
     * 先判断是否存在
     * 再判断是否成功
     * @param index
     * @return
     */
    public static Map<String, Object> deleteIndex(String index) {
        if (!indexExist(index)) {
            rest.put(CODE, NOT_EXIST.getCode());
            rest.put(MSG, NOT_EXIST.getMsg());
        } else {
            DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
            if (dResponse.isAcknowledged()) {
                rest.put(CODE, OPRATION_SUCCESS.getCode());
                rest.put(MSG, OPRATION_SUCCESS.getMsg());
            } else {
                rest.put(CODE, OPRATION_FATLED.getCode());
                rest.put(MSG, OPRATION_FATLED.getMsg());
            }
        }
        return rest;
    }


    /**
     * 判断索引存在
     * @param index
     * @return
     */
    private static Boolean indexExist(String index) {
        IndicesExistsResponse indicesExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        return indicesExistsResponse.isExists();
    }

    /**
     *  添加数据
     * @param map  添加的数据
     * @param index   索引
     * @param type    类型
     * @param id     id
     * @return
     */
    public static Map<String, Object> addData(Map<String, Object> map, String index, String type, String id) {
        IndexResponse response = client.prepareIndex(index, type, id).setSource(map).get();
        String status = response.status().toString();
        if(OK.equals(status.toUpperCase())) {
            rest.put(CODE, OPRATION_SUCCESS.getCode());
            rest.put(MSG, OPRATION_SUCCESS.getMsg());
        } else {
            rest.put(CODE, OPRATION_FATLED.getCode());
            rest.put(MSG, OPRATION_FATLED.getMsg());
        }
        return rest;
    }

    /**
     * 添加数据
     * id 随机生成
     * @param mapObj
     * @param index
     * @param type
     * @return
     */
    public static Map<String, Object> addData(Map<String, Object> mapObj, String index, String type) {
        return addData(mapObj, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 通过ID删除数据
     * @param index
     * @param type
     * @param id
     * @return
     */
    public static Map<String, Object> deleteDataById(String index, String type, String id) {

        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        String status = response.status().toString();
        if(OK.equals(status.toUpperCase())) {
            rest.put(CODE, OPRATION_SUCCESS.getCode());
            rest.put(MSG, OPRATION_SUCCESS.getMsg());
        } else {
            rest.put(CODE, OPRATION_FATLED.getCode());
            rest.put(MSG, OPRATION_FATLED.getMsg());
        }
        return rest;
    }

    public static Map<String,Object> updateData(Map<String,Object> map,String index,String type,String id){
        UpdateRequest request = new UpdateRequest();
        request.index(index).type(type).id(id).doc(map);
        ActionFuture<UpdateResponse> update = client.update(request);
        String s = update.actionGet().status().toString();
        if(OK.equals(s.toUpperCase())){
            rest.put(CODE, OPRATION_SUCCESS.getCode());
            rest.put(MSG, OPRATION_SUCCESS.getMsg());
        } else {
            rest.put(CODE, OPRATION_FATLED.getCode());
            rest.put(MSG, OPRATION_FATLED.getMsg());
        }
        return rest;

    }

    /**
     *查找
     * @param index  索引
     * @param type  类型
     * @param id  ID
     * @param fields  显示字段
     * @return
     */
    public static Map<String, Object> seleDateByid(String index,String type,String id,String fields) {
        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);
        //判断
        if( !StringUtil.isNullOrEmpty(fields)){
            getRequestBuilder.setFetchSource(fields.split(","),null);
        }
        GetResponse documentFields =getRequestBuilder.execute().actionGet();
        return documentFields.getSource();
    }

    /**
     *  分词查询
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param query          查询条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sourField      排序字段
     * @param highlightField 高亮字段

     * @return
     */
    public static List<Map<String,Object>> seleAllDate(String index, String type, QueryBuilder query, Integer size
            , String fields, String sourField, String highlightField){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if(!StringUtil.isNullOrEmpty(type)){
            searchRequestBuilder.setTypes(type.split(","));
        }
        if(!StringUtil.isNullOrEmpty(highlightField)){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }
        searchRequestBuilder.setQuery(query);
        if(!StringUtil.isNullOrEmpty(fields)){
            searchRequestBuilder.setFetchSource(fields.split(","),null);
        }
        if(!StringUtil.isNullOrEmpty(sourField)){
            searchRequestBuilder.addSort(sourField, SortOrder.DESC);
        }
        if(null!=size&& 0<size){
            searchRequestBuilder.setSize(size);
        }
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        if("OK".equals(searchResponse.status().toString().toUpperCase())){
            return setSele(searchResponse,highlightField);
        }


        return null;

    }
    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */

    private static List<Map<String ,Object>> setSele(SearchResponse searchResponse,String highlightField){
        List<Map<String ,Object>> list = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        for (SearchHit searchHit:searchResponse.getHits().getHits()){
            searchHit.getSourceAsMap().put("id",searchHit.getId());
            if(null!=highlightField && !"".equals(highlightField)){
                //正常结果集
                Text[] fragments = searchHit.getHighlightFields().get(highlightField).getFragments();
                if(null!=fragments){
                    for (Text text :fragments){
                        stringBuffer.append(text.toString());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField,stringBuffer.toString());
                }
            }
            list.add(searchHit.getSourceAsMap());
        }
        return list;
    }

}
