package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {
    private String userName;
    private String message;
    private String createdDate;

    public Comment() {
        // Needed by Jackson deserialization
    }

    public Comment(String userName, String message, String createdDate) {
        this.userName = userName;
        this.message = message;
        this.createdDate = createdDate;
    }

    @JsonProperty
    public String getUserName() {
        return this.userName;
    }

    @JsonProperty
    public String getMessage() {
        return this.message;
    }

    @JsonProperty
    public String getCreatedDate() {
        return this.createdDate;
    }
}
