package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import businessLogic.BLFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;

@Named("QueryRides")
@ApplicationScoped
public class QueryRidesBean {

	BLFacade facadeBL=FacadeBean.getBusinessLogic(); // Negozioaren logika sortu
	
	public String atzera() {
		return "main";
	}

}
