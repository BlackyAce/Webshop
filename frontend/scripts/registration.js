
$(document).ready(function() {
  $('#createUser-form').submit(function(e) {
    e.preventDefault();

    var salutation = $('#inputSalutation').val();
    var firstName = $('#inputFirstName').val();
    var lastName = $('#inputLastName').val();
    var email = $('#inputEmail').val();
    var username = $('#inputUserName').val();
    var password = $('#inputPassword').val();
    var streetAddress = $('#inputStreetAdress').val();
    var streetNumber = $('#inputStreetNumber').val();
    var city = $('#inputCity').val();
    var postalCode = $('#inputPostalCode').val();
    var country = $('#inputCountry').val();

    // Prepare the data to be sent in the request body
    var user = {
      salutation: salutation,
      firstname: firstName,
      lastname: lastName,
      email: email,
      username: username,
      password: password,
      streetadress: streetAddress,
      streetnumber: streetNumber,
      city: city,
      postalcode: postalCode,
      country: country,
      admin: false
    };
console.log(user)
    // Send the AJAX request
    $.ajax({
      url: '/user/createUser', // Replace with the actual API endpoint URL
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(user),
      contentType: 'application/json',
      success: function(response) {
        // Handle the success response
        console.log('User created successfully');
        // Add your code to handle the success case
      },
      error: function(error) {
        // Handle the error response
        console.log('An error occurred');
        // Add your code to handle the error case
      }
    });
  });
});

