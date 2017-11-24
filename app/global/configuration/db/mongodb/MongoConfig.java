package global.configuration.db.mongodb;

import lombok.Getter;

import javax.annotation.Nonnull;


@Getter
public class MongoConfig {
    private String databaseName;
    private int port;
    private String host;
    private String uriString;
    private boolean isURI = false;

    MongoConfig(@Nonnull String databaseName, @Nonnull String host, int port) {
        this.databaseName = databaseName;
        this.port = port;
        this.host = host;
    }

    MongoConfig(@Nonnull String uriString) {
        this.uriString = uriString;
        isURI = true;
    }

}
