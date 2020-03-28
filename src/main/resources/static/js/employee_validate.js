$().ready(function(){
	$("#EmployeeInsert").validate({
		rules:{
			designation: "required",
			firstname: {required:true,
				alphabet:true},
			lastname:{required:true,
				alphabet:true},
			username:{minlength:2,
				required: true},
			email:{required:true,
				emailvalidation:true},
			phone:{required:true,
				phoneUS:true},
			date_of_birth:"required",
		},
		messages:{
			designation:"Please select a customer type",
			firstname:{
				required:"Please enter your first name",
				},
			lastname:{
				required:"Please enter your last name",
			},
			date_of_birth:{required:"Please enter your DOB"},
			phone:{required:"Please enter your phone number"},
			email:{required:"Please enter your email"},
			username:{
				minlength:"Username should be atleast 2 characters long",
				required:"Please enter a username"
			},
		}
	});
	
	$("#username").focus(function(){
		var firstname= $("#firstname").val();
		var lastname=$("#lastname").val();
		if(firstname && lastname && !this.value){
			this.value = firstname + "." + lastname;
		}
	});
	
	jQuery.validator.addMethod("alphabet", function(value, element) {
	    return this.optional(element) || /^[A-z]+$/i.test(value);
	}, "Letters only please");
	
	jQuery.validator.addMethod("secvalidation", function(value, element) {
	    return this.optional(element) || /^[A-z0-9]+$/i.test(value);
	}, "Letters or Numbers only please");
	
	jQuery.validator.addMethod("SSNvalidation", function(value, element) {
	    return this.optional(element) || /^[0-9]{3}\-?[0-9]{2}\-?[0-9]{4}$/i.test(value);
	}, "Please Enter a Valid SSN(000-00-0000)");
	
	jQuery.validator.addMethod("addressfilter", function(value, element) {
	    return this.optional(element) || /^[A-z0-9\s]+$/i.test(value);
	}, "Letters or Numbers with spaces only please");
	
	jQuery.validator.addMethod("passwordvalidation", function(value, element) {
	    return this.optional(element) || /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/i.test(value);
	}, "Enter a proper valid strong password");
	
	jQuery.validator.addMethod( "phoneUS", function( phone_number, element ) {
		phone_number = phone_number.replace( /\s+/g, "" );
		return this.optional( element ) || phone_number.length > 9 &&
			phone_number.match( /^(\+?1-?)?(\([2-9]([02-9]\d|1[02-9])\)|[2-9]([02-9]\d|1[02-9]))-?[2-9]\d{2}-?\d{4}$/ );
	}, "Please specify a valid phone number(+1(XXX)-XXX-XXXX)" );
	
	jQuery.validator.addMethod("emailvalidation", function(value, element) {
	    return this.optional(element) || /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i.test(value);
	}, "Enter a proper email format");
	
	$("#EmployeeUpdate").validate({
		rules:{
			firstname: {required:true,
				alphabet:true},
			lastname:{required:true,
				alphabet:true},
			email:{required:true,
				emailvalidation:true},
			phone:{required:true,
				phoneUS:true},
			date_of_birth:"required",
			ssn:{required:true,
				SSNvalidation:true},
			secquestion1:{required:true,
				secvalidation:true},
			secquestion2:{required:true,
				secvalidation:true}
		},
		messages:{
			designation:"Please select a customer type",
			firstname:{
				required:"Please enter your first name",
				},
			lastname:{
				required:"Please enter your last name",
			},
			date_of_birth:{required:"Please enter your DOB"},
			phone:{required:"Please enter your phone number"},
			email:{required:"Please enter your email"},
			ssn:{required:"Please enter your ssn"},
			secquestion1:{required:"Please answer the security question"},
			secquestion2:{required:"Please answer the security question"}
		}
	});
	
	$("#EmployeeDelete").validate({
		rules:{
			firstname: {required:true,
				alphabet:true},
			lastname:{required:true,
				alphabet:true},
			username:{minlength:2,
				required: true}
		},
		messages:{
			firstname:{
				required:"Please enter your first name",
				},
			lastname:{
				required:"Please enter your last name",
			},
			username:{
				minlength:"Username should be atleast 2 characters long",
				required:"Please enter a username"
			}
		}
	});
	
	$("#SetPassword").validate({
		rules:{
			username:{minlength:2,
				required: true},
				password:{minlength:6,
					required: true,
					passwordvalidation: true},
				confirmpassword:{minlength:6,
					required: true,
					equalTo:"#password",
					passwordvalidation: true}	
		},
		messages:{
			username:{
				minlength:"Username should be atleast 2 characters long",
				required:"Please enter a username"
			},
			password:{
				minLength:"Password should be atleast 6 characters long",
				required:"Please enter a password"
			},
			confirmpassword:{
				minLength:"Password should be atleast 6 characters long",
				required:"Please enter a password",
				equalTo:"Passwords dont match"
			}
		}
	});
	
	$("#ChangePassword").validate({
		rules:{
			username:{minlength:2,
				required: true},
				oldpassword:{required:true},
				newpassword:{minlength:6,
					required: true,
					passwordvalidation: true,
					notEqual:"#oldpassword"},
				confirmpassword:{minlength:6,
					required: true,
					equalTo:"#newpassword",
					passwordvalidation: true}	
		},
		messages:{
			username:{
				minlength:"Username should be atleast 2 characters long",
				required:"Please enter a username"
			},
			oldpassword:"Please enter your old password",
			newpassword:{
				minLength:"Password should be atleast 6 characters long",
				required:"Please enter your new password",
				notEqual:"Old and New Passwords cant be same"
			},
			confirmpassword:{
				minLength:"Password should be atleast 6 characters long",
				required:"Please confirm your password",
				equalTo:"Passwords dont match"
			}
		}
	});
	
})