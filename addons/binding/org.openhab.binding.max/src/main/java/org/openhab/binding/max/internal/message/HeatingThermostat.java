/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.max.internal.message;

import java.util.Date;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.State;

/**
 * MAX!Cube heating thermostat.
 * 
 * @author Andreas Heil (info@aheil.de)
 * @author Marcel Verpaalen - OH2 update
 * @since 1.4.0
 */
public class HeatingThermostat extends Device {
	private ThermostatModeType mode;

	/** Valve position in % */
	private int valvePosition;

	/** Temperature setpoint in degrees celcius */
	private double temperatureSetpoint;

	/** Actual Temperature in degrees celcius */
	private double temperatureActual;

	/** Date setpoint until the termperature setpoint is valid */
	private Date dateSetpoint;

	/** Device type for this thermostat **/
	private DeviceType deviceType = DeviceType.HeatingThermostat;

	public HeatingThermostat(DeviceConfiguration c) {
		super(c);
	}

	@Override
	public DeviceType getType() {
		return deviceType;
	}

	/**
	 * Sets the DeviceType for this thermostat.
	 * @param DeviceType as provided by the C message
	 */
	void setType (DeviceType type) {
		this.deviceType = type;
	}


	/**
	 * Returns the current mode of the thermostat.
	 */
	public StringType getModeString() {
		return new StringType (this.mode.toString());
	}

	/**
	 * Returns the current mode of the thermostat.
	 */
	public ThermostatModeType getMode() {
		return (ThermostatModeType) this.mode;
	}

	void setMode(ThermostatModeType mode) {
		if(this.mode != mode) setUpdated (true);
		this.mode = mode;
	}

	/**
	 * Sets the valve position for this thermostat.
	 * @param valvePosition the valve position as provided by the L message
	 */
	public void setValvePosition(int valvePosition) {
		if(	this.valvePosition != valvePosition) setUpdated (true);
		this.valvePosition = valvePosition;
	}

	/**
	 * Returns the current valve position  of this thermostat in percent. 
	 *
	 * @return 
	 * 			the valve position as <code>DecimalType</code>
	 */
	public DecimalType getValvePosition() {
		return new DecimalType(this.valvePosition);
	}

	public void setDateSetpoint(Date date) {
		this.dateSetpoint = date;
	}

	public Date getDateSetpoint() {
		return dateSetpoint;
	}

	/**
	 * Sets the actual temperature for this thermostat. 
	 * @param value the actual temperature raw value as provided by the L message
	 */
	public void setTemperatureActual(double value) {
		if(this.temperatureActual != value ) setUpdated (true);
		this.temperatureActual = value ;
	}

	/**
	 * Returns the measured temperature  of this thermostat. 
	 * 0�C is displayed if no actual is measured. Temperature is only updated after valve position changes
	 *
	 * @return 
	 * 			the actual temperature as <code>DecimalType</code>
	 */
	public State getTemperatureActual() {
		return new DecimalType(this.temperatureActual);
	}

	/**
	 * Sets the setpoint temperature for this thermostat. 
	 * @param value the setpoint temperature raw value as provided by the L message
	 */
	public void setTemperatureSetpoint(int value) {
		if(Math.abs(this.temperatureSetpoint - (value / 2.0)) > 0.1 ) setUpdated (true);
		this.temperatureSetpoint = value / 2.0;
	}

	/**
	 * Returns the setpoint temperature  of this thermostat. 
	 * 4.5°C is displayed as OFF, 30.5°C is displayed as On at the thermostat display.
	 *
	 * @return 
	 * 			the setpoint temperature as <code>DecimalType</code>
	 */
	public State getTemperatureSetpoint() {
		return new DecimalType(this.temperatureSetpoint);
	}
}
