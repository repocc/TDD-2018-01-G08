package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class Ticket {
    private Integer id;
    private String authorUserName;
    private String takenUserName;
    private String createdDate;
    private String lastModifiedDate;
    private String title;
    private String description;
    private String type;
    private Set<Field> requiredFields = new HashSet<Field>();
    private Set<Field> optionalFields = new HashSet<Field>();
    private Set<Comment> comments = new HashSet<Comment>();
    private String state;
    private String important;


    public Ticket() {
        // Needed by Jackson deserialization
    }

    public Ticket(String authorUserName, String takenUserName, String createdDate, String lastModifiedDate,
                  String title, String description, String type, String important) {
        this.authorUserName = authorUserName;
        this.takenUserName = takenUserName;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.title = title;
        this.description = description;
        this.type = type;
        this.state = "";
        this.important = important;
    }

    @JsonProperty
    public Integer getTicketId() {
        return this.id;
    }

    @JsonProperty
    public String getAuthorUserName() {
        return this.authorUserName;
    }

    @JsonProperty
    public String getTakenUserName() {
        return this.takenUserName;
    }

    @JsonProperty
    public String getTitle() {
        return this.title;
    }

    @JsonProperty
    public String getDescription() {
        return this.description;
    }

    @JsonProperty
    public String getType() {
        return this.type;
    }

    @JsonProperty
    public String getImportant() {
        return this.important;
    }

    @JsonProperty
    public String getCreatedDate() {
        return this.createdDate;
    }

    @JsonProperty
    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    @JsonProperty
    public Set<Field> getRequiredFields() {
        return this.requiredFields;
    }

    @JsonProperty
    public Set<Field> getOptionalFields() {
        return this.optionalFields;
    }

    @JsonProperty
    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setId(Integer ticketId) {
        this.id = ticketId;
    }

    public void setTakenUserName(String userName) {
        this.takenUserName = userName;
    }

    public Boolean addComment(Comment comment) {
        this.comments.add(comment);
        return true;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void addRequiredField(Field newField) {
        this.requiredFields.add(newField);
    }

    public void addOptionalField(Field newField) {
        this.optionalFields.add(newField);
    }

    public boolean isAvailable() {
        return this.takenUserName.equals("");
    }

    public String ruleData() {
        String data = "{\"title\" \""+title+"\", \"description\" \""+description+"\",\"type\" \""+type+"\",\"important\" "+important+"}";
        return data;
    }
}
