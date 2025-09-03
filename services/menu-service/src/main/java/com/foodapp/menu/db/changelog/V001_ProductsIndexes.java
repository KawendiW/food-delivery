package com.foodapp.menu.db.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@ChangeUnit(id = "products-indexes", order = "001", author = "kawendix")
@AllArgsConstructor
public class V001_ProductsIndexes {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void createIndexes() {
        mongoTemplate.indexOps("products")
                .createIndex(new Index()
                        .on("shopId", Sort.Direction.ASC)
                        .on("sku", Sort.Direction.ASC)
                        .unique());

        mongoTemplate.indexOps("products")
                .createIndex(new Index()
                        .on("shopId", Sort.Direction.ASC)
                        .on("tags", Sort.Direction.ASC));
    }

    @RollbackExecution
    public void rollback(){
        // t.indexOps("products").dropIndex("shopId_1_sku_1");
        // t.indexOps("products").dropIndex("shopId_1_tags_1");
    }

}
