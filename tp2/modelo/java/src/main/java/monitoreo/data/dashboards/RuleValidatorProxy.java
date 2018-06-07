package monitoreo.data.dashboards;

import com.ar.fiuba.tdd.clojure.*;
import com.ar.fiuba.tdd.clojure.estado.estado.Estado;

public class RuleValidatorProxy {

    public RuleValidatorProxy() {
        // Needed by Jackson deserialization
    }

    public Estado initialize(String rules) {
		Estado estado = data_processor.initialize_processor(rules);
		return estado;
    }

	public Estado process_data(Estado state, String data) {
		clojure.lang.PersistentVector clojureVecRes = data_processor.process_data(state, data);
		Estado stateRes = data_processor.drop_signals(clojureVecRes);
		return stateRes;
	}

	public Long query_counter(Estado state, String counter_name) {
		Long res = data_processor.query_counter(state, counter_name, "()");
		return res;
	}
}
