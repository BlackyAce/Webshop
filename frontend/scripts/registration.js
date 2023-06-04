document.getElementById("createUser-form").addEventListener("submit", function(event){
    event.preventDefault();

    const formData = new FormData();

    formData.append("inputFirstName", document.querySelector("#inputFirstName").value);
    formData.append("inputLastName", document.querySelector("#inputLastName").value);
    formData.append("inputEmail", document.querySelector("#inputEmail").value);
    formData.append("inputPassword", document.querySelector("#inputPassword").value);
    formData.append("inputStreetAdress", document.querySelector("#inputStreetAdress").value);
    formData.append("StreetNumber", document.querySelector("#StreetNumber").value);
    formData.append("City", document.querySelector("#City").value);
    formData.append("PostalCode", document.querySelector("#PostalCode").value);
    formData.append("Country", document.querySelector("#Country").value);
    

    $.ajax({
        url: 'http://localhost:8080/user',   
        method: 'post',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify ({

            first_name: document.querySelector("#inputFirstName").value,
            last_name: document.querySelector("#inputLastName").value,
            email: document.querySelector("#inputEmail").value,
            password: document.querySelector("#inputPassword").value,
            street_adress: document.querySelector("#inputStreetAdress").value,
            street_number: document.querySelector("#StreetNumber").value,
            city: document.querySelector("#City").value,
            postal_code: document.querySelector("#PostalCode").value,
            country: document.querySelector("#Country").value
            
        }), 
        success: function (response){console.log("Registration successful")},
        error: function (error){console.log(error)}
    });
});
