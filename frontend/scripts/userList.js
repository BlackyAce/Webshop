$(document).ready(function() {
    $.ajax({
      url: 'http://localhost:8080/user', 
      headers: { "Authorization": sessionStorage.getItem("token") },
      success: function(response) {
        addUsersToList(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  
    function addUsersToList(users) {
      const userTableBody = $('#userTableBody');
  
      users.forEach(function(user) {
        const row = $("<tr>");
        row.append($("<td>").text(user.id));
        row.append($("<td>").text(user.name));
        row.append($("<td>").text(user.email));
        row.append($("<td>").text(user.admin ? 'Yes' : 'No'));
        const editButton = $("<td><button id='editButton'>Edit</button></td>");
      editButton.click(function () {
        displayUserForEditing(user);
      });
      row.append(editButton);
        userTableBody.append(row);
      });
    }
  });


  function displayUserForEditing(user) {
    let userId = user.id;
    let userListContainer = document.getElementById("userListContainer");
    let userEditContainer = document.getElementById("userEditContainer");
  
    userListContainer.style.display = "none";
    userEditContainer.style.display = "block"
  
    $("#editUserId").val(userId);
    $("#editUserName").val(user.name);
    $("#editUserFirstName").val(user.firstname);
    $("#editUserLastName").val(user.lastname);
    $("#editUserEmail").val(user.email);
    $("#editUserStreetAdress").val(user.name);
    $("#editUserStreetNumber").val(user.streetnumber);
    $("#editUserCity").val(user.city);
    $("#editUserPostalCode").val(user.postalcode);
    $("#editUserContry").val(user.contry);



    $("#editUserActive").prop("checked", user.active);
  
    document.getElementById("deleteEditButton").addEventListener("click", function () {
        deleteUser(userId);
    });
  
  }
  document.getElementById("cancelEditButton").addEventListener("click", function () {
    $("#userEditContainer").hide();
    $("#userListContainer").show();
  });
  
  function deleteUser(userId ) {
    if (confirm("Are you sure you want to delete this user?")) {
      $.ajax({
        url: "http://localhost:8080/user/" + userId,
        method: "DELETE",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (response) {
          console.log("Deleted user:", response);
          location.reload();
  
        },
        error: function (xhr, status, error) {
          console.error("Delete request failed. Status:", status, "Error:", error);
        }
      });
    } else {
      location.reload();
    }
  }