package org.example.Usefull_Tools;

//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//public class UniqueFieldValuesWithScroll {
//
//    public static void main(String[] args) throws IOException {
//        // Elasticsearch cluster connection details
//        String hostname = "localhost";
//        int port = 9200;
//        String scheme = "http"; // or "https" if using SSL/TLS
//
//        // Index and field for which you want to retrieve unique values
//        String indexName = "your_index_name";
//        String fieldName = "your_field_name";
//
//        // Path to the output text file
//        String outputFile = "unique_values.txt";
//
//        // Initialize Elasticsearch client
//        try (RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(new HttpHost(hostname, port, scheme)))) {
//
//            // Retrieve unique values using scroll
//            Set<Object> uniqueValues = getUniqueFieldValuesWithScroll(client, indexName, fieldName);
//
//            // Write unique values to a text file
//            writeToFile(uniqueValues, outputFile);
//            System.out.println("Unique values of field '" + fieldName + "' written to " + outputFile);
//
//        }
//    }
//
//    public static Set<Object> getUniqueFieldValuesWithScroll(RestHighLevelClient client,
//                                                             String indexName,
//                                                             String fieldName) throws IOException {
//        Set<Object> uniqueValues = new HashSet<>();
//
//        // Prepare search request with scroll
//        SearchRequest searchRequest = new SearchRequest(indexName);
//        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        searchSourceBuilder.fetchSource(false); // Disable fetching _source for better performance
//        searchSourceBuilder.size(100); // Adjust size as needed for your data
//        searchRequest.source(searchSourceBuilder);
//
//        // Execute initial search request
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        // Scroll until no hits are returned
//        while (searchResponse.getHits().getHits().length > 0) {
//            for (SearchHit hit : searchResponse.getHits().getHits()) {
//                Object fieldValue = hit.getSourceAsMap().get(fieldName);
//                if (fieldValue != null) {
//                    uniqueValues.add(fieldValue);
//                }
//            }
//
//            // Scroll for the next batch of hits
//            String scrollId = searchResponse.getScrollId();
//            searchRequest = new SearchRequest();
//            searchRequest.scroll(TimeValue.timeValueMinutes(1L));
//            searchRequest.scrollId(scrollId);
//            searchResponse = client.scroll(searchRequest, RequestOptions.DEFAULT);
//        }
//
//        return uniqueValues;
//    }
//
//    public static void writeToFile(Set<Object> uniqueValues, String outputFile) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
//            for (Object value : uniqueValues) {
//                writer.write(value.toString());
//                writer.newLine();
//            }
//        }
//    }
//}

