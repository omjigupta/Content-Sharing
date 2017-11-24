package global.configuration.db.mongodb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.typesafe.config.Config;
import global.exceptions.CustomException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Getter
public class MongoDBConnection {

    private final MongoConfig mongoConfig;
    private Datastore dataStore;
    private Logger.ALogger logger = Logger.of(MongoDBConnection.class);


    @Inject
    public MongoDBConnection(Config configuration) {
        final String uri = configuration.getString(MongoConfigConstants.URI);
        final String databaseName = configuration.getString(MongoConfigConstants.DATABASE_NAME);
        final String host = configuration.getString(MongoConfigConstants.HOST);
        final int port = configuration.getInt(MongoConfigConstants.PORT);
        logger.info("Initialising Mongo DB Connection with following parameters.\n Database Name: {} \n Host: {} \n Port: {} \n URI: {}\n",databaseName,host,port,uri);
        mongoConfig = StringUtils.isNotBlank(uri) ? new MongoConfig(uri) : new MongoConfig(databaseName, host, port);
        init(mongoConfig);
    }


    private void init(MongoConfig mongoConfig) {
        try {
            final Morphia morphia = new Morphia();
            MongoClient mongoClient;
            if (mongoConfig.isURI()) {
                mongoClient = new MongoClient(new MongoClientURI(mongoConfig.getUriString()));
            } else {
                mongoClient = new MongoClient(mongoConfig.getHost(), mongoConfig.getPort());
            }
            dataStore = morphia.createDatastore(mongoClient, mongoConfig.getDatabaseName());
            if(dataStore != null) {
                logger.info("Successfully initialised Mongo DB");
            }else{
                logger.warn("MongoDB initialisation not confirmed");
            }
            Json.mapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }catch (Exception e){
            throw new CustomException(e, "Failed to Initialise MongoDB Connection");
        }
    }


    static class MongoConfigConstants {
        static final String DATABASE_NAME = "mongodb.database-name";
        static final String PORT = "mongodb.port";
        static final String HOST = "mongodb.host";
        static final String URI = "mongodb.uri";
    }
}

