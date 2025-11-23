package eredua.bean;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Driver;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.Date;

@Named("CreateRides")
@ApplicationScoped
public class CreateRidesBean {
	private String departCity;
	private String arrivalCity;
	private int seats;
	private float price;
	private Date data;
	private String driverEmail="testDriver@gmail.com";

	BLFacade facadeBL=FacadeBean.getBusinessLogic(); // Negozioaren logika sortu
	
	public void addRide() throws RideMustBeLaterThanTodayException, RideAlreadyExistException {
		facadeBL=FacadeBean.getBusinessLogic();
		facadeBL.createRide(departCity, arrivalCity, UtilDate.trim(data), seats, price, driverEmail);
		MainBean.addBidaiak(departCity, arrivalCity, UtilDate.trim(data), seats, price, new Driver(driverEmail, driverEmail));
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

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDriverEmail() {
		return driverEmail;
	}

	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}
	
	public String atzera() {
		return "main";
	}
	
}
