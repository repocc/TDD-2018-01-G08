package monitoreo.data.dashboards;

import com.ar.fiuba.tdd.clojure.estado.estado.Estado;
import com.fasterxml.jackson.annotation.JsonProperty;
import monitoreo.data.dashboards.users.User;
import monitoreo.data.tickets.Project;
import monitoreo.data.tickets.Ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Dashboard {
    private Integer id;
    private String authUserName;
    private String name;
    private String createdDate;
    private Boolean enable;
    private List<Rule> rules = new ArrayList<Rule>();
    private HashMap<String, User> users = new HashMap<String, User>();
    private Project project;
    private RuleValidatorProxy ruleValidatorProxy = new RuleValidatorProxy();
    private Estado state;

    public Dashboard() {
        // Needed by Jackson deserialization
    }

    public Dashboard(String authUserName,String name, Boolean enable, String createdDate) {
        this.name = name;
        this.enable = enable;
        this.authUserName = authUserName;
        this.createdDate = createdDate;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Boolean getEnable() {
        return enable;
    }

    @JsonProperty
    public String getAuthUserName() {
        return authUserName;
    }

    @JsonProperty
    public String getCreatedDate() {
        return createdDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean addUserToDashboard(User user) {
        String userName = user.getUserName();
        if (!existsUser(userName)) {
            users.put(userName, user);
            user.addDashboard(this);
            return true;
        }
        return false;
    }

    public Boolean addRuleToDashboard(Rule rule) {
        rules.add(rule);
        return true;
    }

    private boolean existsUser(String userName) {
        return users.containsKey(userName);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Boolean addRule(Rule rule) {
        rules.add(rule);
        return true;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public Collection<User> getUsers() {
        return users.values();
    }

    public List<Result> rulesResults() {
        String rules = getRulesString();
        state = ruleValidatorProxy.initialize(rules);
        Collection<Ticket> tickets = project.projectTickets();
        for (Ticket ticket : tickets) {
            state = ruleValidatorProxy.process_data(state, ticket.ruleData());
        }
        List<Result> results = new ArrayList<Result>();
        for (Rule rule : this.rules) {
            String ruleName = rule.getName();
            Result result = new Result(ruleName);
            Long ruleValue = ruleValidatorProxy.query_counter(state,ruleName);
            result.setValue(ruleValue);
            results.add(result);
        }
        return results;
    }

    private String getRulesString() {
        String response = "(";
        for (Rule rule : this.rules) {
            response += rule.getQuery();
        }
        response += ")";
        return response;
    }
}
