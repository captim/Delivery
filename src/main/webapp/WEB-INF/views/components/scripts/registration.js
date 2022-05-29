function uniqueUsernameValidation(usernameInput) {
    $.ajax({
        url:"/anonymous/uniqueUsername?username=" + usernameInput.value,
        type:"get",
        complete: [
            function (response) {
                let jsonResponse = JSON.parse(response.responseText);
                let message = jsonResponse.message;
                let value = jsonResponse.value;
                console.log("Message: " + message);
                if (value) {
                    usernameInput.style.backgroundColor = "white";
                } else {
                    usernameInput.style.backgroundColor = "pink";
                    alert(message);
                }
            }
        ]
    });
}
function uniqueEmailValidation(emailInput) {
    $.ajax({
        url:"/anonymous/uniqueEmail?email=" + emailInput.value,
        type:"get",
        complete: [
            function (response) {
                let jsonResponse = JSON.parse(response.responseText);
                let message = jsonResponse.message;
                let value = jsonResponse.value;
                console.log("Message: " + message);
                if (value) {
                    emailInput.style.backgroundColor = "white";
                } else {
                    emailInput.style.backgroundColor = "pink";
                    alert(message);
                }
            }
        ]
    });
}
function createCustomer() {
    object = {};
    new FormData(document.getElementById("signup-form")).forEach((value, key) => object[key] = value);
    var formData = JSON.stringify(object);
    console.log(formData);
    $.ajax({
        url: "/createCustomer",
        type: "post",
        data: formData,
        contentType: "application/json",
        complete: [
            function (response) {
                console.log(response);
                let jsonResponse = JSON.parse(response.responseText);
                if (response.status === 201) {
                    alert(jsonResponse.message);
                    window.location.href = "/login";
                } else if (response.status === 422) {
                    alert(jsonResponse.message);
                } else {
                    alert("Unexpected error!")
                }
            }
        ]
    });
}