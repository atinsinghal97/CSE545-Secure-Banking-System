package constants;

import java.math.BigDecimal;

public final class Constants {

    private Constants() {
            // restrict instantiation
    }

    public static final String TIER1 = "tier1";
    public static final String TIER2 = "tier2";
    public static final String TIER3 = "tier3";
    public static final String ADMIN = "admin";
    public static final String INDIVIDUAL = "individual";
    public static final String CUSTOMER = "customer";
    public static final int DEFAULT_TIER1 = 9;
    public static final int DEFAULT_TIER2 = 3;
    public static final int DEFAULT_ADMIN = 4;
    public static final String TRANSFER = "transfer";
    public static final String DEBIT = "debit";
    public static final String CREDIT = "credit";
    public static final String CHEQUE = "cc";
    public static final BigDecimal THRESHOLD_AMOUNT = BigDecimal.valueOf(1000);


    public static final String userNamePattern = "^[^!@#~$%^&*\\\\(\\\\)-\\\\+=\\\\[\\\\]\\\\{\\\\};:'\\\"<>,/\\\\?`].+{5,}$";
    public static final String userNameErrorMessage = "Username cannot contain special characters. It can only contain alphabets, numbers, underscores and periods.";

    public static final String phoneNumberPattern = "^(\\+?1-?)?(\\([2-9]([02-9]\\d|1[02-9])\\)|[2-9]([02-9]\\d|1[02-9]))-?[2-9]\\d{2}-?\\d{4}$";
    public static final String phoneNumberErrorMessage = "Please specify a valid phone number(+1(XXX)-XXX-XXXX)";
    
    public static final String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$";
    public static final String passwordErrorMessage = "Password must be between 6 and 16 digits long and include at least one numeric digit and one special character.";
    
    public static final String userRolePattern = "^(customer)|(merchant)$";
    public static final String userRoleErrorMessage = "You can register only as a customer or a merchant.";
    
    public static final String employeeRolePattern = "^(tier1)|(tier2)$";
    public static final String employeeRoleErrorMessage = "You can register only as a Tier1 or a Tier2 Employee.";
    
    public static final String dateOfBirthPattern = "^\\d{2}\\/\\d{2}\\/\\d{4}$";
    public static final String dateOfBirthErrorMessage = "Date of Birth should be of the form MM/DD/YYYY in numbers.";
    
    public static final String ssnPattern = "^[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{4}$";
    public static final String ssnErrorMessage = "SSN must consist of numbers of length 9";
    
    public static final String zipPattern = "^\\d+$";
    public static final String zipErrorMessage = "Zip code must be numbers";

    public static final String namePattern = "^[A-z\\s]+$";
    public static final String nameMessage = "Name can contain only words";

    public static final String secPattern = "^[A-z0-9]+$";
    public static final String secMessage = "Security answers can contain only words or numbers";

    public static final String addressPattern = "^[A-z0-9\\s,\\-\\#]+$";
    public static final String addressErrorMessage = "Address cannot have special characters other than hyphen(-), comma(,) and hash(#)";

    public static final String locationPattern = namePattern;
    public static final String locationErrorMessage = "Location must only be words";
}