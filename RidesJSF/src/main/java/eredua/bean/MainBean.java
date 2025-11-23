package eredua.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;


@Named("Main")
@ViewScoped
public class MainBean implements Serializable{
	private String departCity; 
	private String arrivalCity; 
	private Date rideDate;
	private static List<Ride> bidaiak=new ArrayList<Ride>();
	
	private Date date;
	private List<Ride> unekoBidaiak;
	
	BLFacade facadeBL=FacadeBean.getBusinessLogic();
	Date d = UtilDate.trim(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000));
	
	public MainBean() throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
////		facadeBL.createRide("Bilbao", "Donostia", d, 3, 10, "testDriver");
//		bidaiak.add(new Ride("Bilbao", "Donostia", d, 3, 10, new Driver("testDriver", "testDriver")));
////		facadeBL.createRide("Gasteiz", "Irun", d, 4, 12, "testDriver");
//		bidaiak.add(new Ride("Gasteiz", "Irun", d, 4, 12, new Driver("testDriver", "testDriver")));
////		facadeBL.createRide("Gasteiz", "Donostia", d, 2, 20, "testDriver");
//		bidaiak.add(new Ride("Gasteiz", "Donostia", d, 2, 20, new Driver("testDriver", "testDriver")));
	}
	public String queryRides() {
        return "QueryRides"; 
    }
	
	public String createRides() {
        return "CreateRides";
    }
	public List<Ride> getBidaiak() {
		return bidaiak;
	}
	public void setBidaiak(List<Ride> bidaiak) {
		this.bidaiak = bidaiak;
	}
	public static void addBidaiak(String from, String to,  Date date, int nPlaces, float price, Driver driver) {
		bidaiak.add(new Ride(from, to, date, nPlaces, price, driver));
	}
	
    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }
    
    public List<String> getDepartCities() {
    	return bidaiak.stream()
                .map(Ride::getFrom)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public List<String> getArrivalCities() {
        if (departCity == null || departCity.isEmpty()) return new ArrayList<>();

        return bidaiak.stream()
                .filter(r -> r.getFrom().equals(departCity))
                .map(Ride::getTo)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public Date getDate() {
		return date;
	}
    
	public void setDate(Date date) {
		this.date = date;
	}
	
	public List<Ride> getUnekoBidaiak() {
		return unekoBidaiak;
	}
	
	public void setUnekoBidaiak(List<Ride> unekoBidaiak) {
		this.unekoBidaiak = unekoBidaiak;
	}
	
    
	public void listenerDate() {
        if (departCity != null && arrivalCity != null && date != null) {
            Date day = UtilDate.trim(date);
            unekoBidaiak = bidaiak.stream()
                .filter(r -> r.getFrom().equals(departCity)
                          && r.getTo().equals(arrivalCity)
                          && UtilDate.trim(r.getDate()).equals(day))
                .collect(Collectors.toList());

            if (unekoBidaiak.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "No rides for selected date", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Rides found: " + unekoBidaiak.size(), null));
            }
        } else {
        	if(unekoBidaiak!=null) {
        		unekoBidaiak.clear();
        	}
        }
    }
		
	public void listenerFrom(AjaxBehaviorEvent event) {
		if (departCity != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Selected Depart City: " + departCity));
        }
	}
	public void listenerTo(AjaxBehaviorEvent event) {
		FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Arrival selected: " + arrivalCity));
	}

}
