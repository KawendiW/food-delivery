package com.foodapp.menu.db.changelog;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@ChangeUnit(id = "shops-indexes", order = "001", author = "kawendix")
@AllArgsConstructor
public class V001_ShopIndexes {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void createIndexes() {
        mongoTemplate.indexOps("shops")
                .createIndex(new Index().on("slug", Sort.Direction.ASC).unique());
    }

    @RollbackExecution
    public void rollback(){
        mongoTemplate.indexOps("shops").dropIndex("slug_1");
    }
}
