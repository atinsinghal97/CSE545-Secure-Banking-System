function loadError()
{
	var t = document.getElementById("errorMsg").value;
	if(t)
		{
			alert(t);
		}
}


function ButtonFormAction(path, params) {
    var form = document.getElementById("dummyform");
    form.setAttribute("method", "post");
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var atrrib = document.createElement("input");
            atrrib.setAttribute("name", key);
            atrrib.setAttribute("value", params[key]);
            form.appendChild(atrrib);
        }
    }
    document.body.appendChild(form);
    form.submit();
}

function ViewTransactions(accountnum)
{
	var params = { accountid: accountnum };
	ButtonFormAction("/transactions", params);
}

function OpenPayments(accountnum)
{
	var params = { accountid: accountnum };
	ButtonFormAction("/payments", params);
}

function DepositWithrawal(accountnum)
{
	var params = { accountid: accountnum };
	ButtonFormAction("/depositwithdrawal", params);
}

function OpenMerchPayments(accountnum)
{
	var params = { accountid: accountnum };
	ButtonFormAction("/OpenPayments", params);
}
