package com.ninjaone.rmm.common;

public class GlobalMessages {
	
	public static final String OBJECT_DOES_NOT_EXIST_WITH_ID_MSG = "The %s with ID %s does not exist!";
	public static final String CANNOT_CREATE_WITH_MSG = "Sorry, but you can not create a new %s with %s %s, it already exist in the database!";
	public static final String CANNOT_CREATE_CUSTOMER_WITHOUT_DEVICES = "Sorry, but you can not create a new %s without %s!";
	public static final String OBJECT_ALREADY_EXIST_MSG = "%s already exist in database!";
	public static final String ATTRIBUTE_CANNOT_BE_NULL_OR_EMPTY = "the %s attribute cannot be null or empty!";
	public static final String OBJECT_HAS_DEPENDENCY_MSG = "Sorry, but you can not delete this %s because it has dependency!";
	public static final String DEVICE_STR = "device";
	public static final String DEVICE_AND_SERVICE_DO_NOT_EXIST_BUT_CLIENT_WAS_CREATED_MSG = "The id of devices %s and services %s do not exist in our database and were not included in the customer! "
			+ "But the customer was created with the id %s, and the other devices and services from were inserted! ";
	public static final String SERVICE_DO_NOT_EXIST_BUT_CLIENT_WAS_CREATED_MSG = "The id of services %s does not exist in our database and were not included in the customer! "
			+ "But the the ID %s client has been successfully updated with the others! ";
	public static final String SORRY_BUT_YOU_CAN_NOT_CREATE_A_NEW_PRICE_PER_SERVICE_WITH_SERVICE_S_AND_OPERATIONAL_SYSTEM_S_ALREADY_EXIST_IN_THE_DATABASE_WITH_ID_S = "Sorry, but you can not create a new price per service, with service %s and operational system %s already exist in the database with ID %s!";

}
