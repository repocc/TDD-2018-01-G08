package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class TicketType {
    private String authUserName;
    private String type;
    private List<Field> requiredFields = new ArrayList<Field>();
    private List<Field> optionalFields = new ArrayList<Field>();
    private List<State> states = new ArrayList<State>();

    public TicketType() {
        // Needed by Jackson deserialization
    }

    public TicketType(String authUserName, String type, List<Field> requiredFields, List<Field> optionalFields, List<State> states) {
        this.authUserName = authUserName;
        this.type = type;
        this.requiredFields = requiredFields;
        this.optionalFields = optionalFields;
        this.states = states;
    }

    @JsonProperty
    public List<Field> getRequiredFields() {
        return this.requiredFields;
    }

    @JsonProperty
    public List<Field> getOptionalFields() {
        return this.optionalFields;
    }

    @JsonProperty
    public List<State> getStates() {
        return this.states;
    }


    public String getAuthUserName() {
        return this.authUserName;
    }

    public String getType() {
        return this.type;
    }

    public String getNextState(String actualStateName) {
        Iterator<State> itr = states.iterator();
        while (itr.hasNext()) {
            State state = itr.next();
            String stateName = state.getName();
            if ((stateName == actualStateName) && (itr.hasNext())){
                State nextState = itr.next();
                return nextState.getName();
            }
        }
        return states.get(states.size() - 1).getName();
    }

    public String getFirstState() {
        return this.states.get(0).getName();
    }
}
