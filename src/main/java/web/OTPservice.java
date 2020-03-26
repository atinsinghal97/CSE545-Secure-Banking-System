package web;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class OTPservice {
	private LoadingCache<String,Integer> CacheforOTP;
	private static final Integer OTP_EXPIRY =5; 
	public OTPservice() {
		super();
		CacheforOTP = CacheBuilder.newBuilder()
				.expireAfterWrite(OTP_EXPIRY, TimeUnit.MINUTES).build(new CacheLoader<String,Integer>(){
					public Integer load(String key) {
						return 0;
					}
				});
	}
	public  int generateNewOTP(String name) {
		// TODO Auto-generated method stub
		Random random = new Random();
		int otp =100000 + random.nextInt(900000);
		CacheforOTP.put(name,otp);
		return otp;
	}
	
	public int getOTP(String name) {
	try {
	return CacheforOTP.get(name);	
	}catch(Exception e) {
		return 0;
	}
	}

	public void removeOtp(String name) {
		CacheforOTP.invalidate(name);
	}
}
