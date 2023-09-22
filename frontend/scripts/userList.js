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
        const row = $('<tr>');
        row.append($('<td>').text(user.id));
        row.append($('<td>').text(user.username));
        row.append($('<td>').text(user.email));
        row.append($('<td>').text(user.admin ? 'Yes' : 'No'));
        const editButton = $("<td><button id='editButton'>Edit</button></td>");
      editButton.click(function () {
        displayUserForEditing(product);
      });
      row.append(editButton);
        userTableBody.append(row);
      });
    }
  });
  function displayUserForEditing(user) {
    let userId = user.id;
   
    let userListContainer = document.getUserById("userListContainer");
    let userEditContainer = document.getUserById("userEditContainer");
  
    userListContainer.style.display = "none";
    userEditContainer.style.display = "block";
  
    $("#editUserId").val(userId);
    $("#editUserName").val(user.name);
    $("#editUserDescription").val(user.description);
    $("#editUserActive").prop("checked", user.active);
  
    document.getElementById("deleteEditButton").addEventListener("click", function () {
        deleteProduct(userId);
    });
  
  }
  document.getElementById("cancelEditButton").addEventListener("click", function () {
    $("#userEditContainer").hide();
    $("#userListContainer").show();
  });
  
  function deleteUser(userId, ) {
    if (confirm("Are you sure you want to delete this product?")) {
      $.ajax({
        url: "http://localhost:8080/products/" + userId,
        method: "DELETE",
        headers: { "Authorization": sessionStorage.getItem("token") },
        success: function (response) {
          console.log("Deleted product:", response);
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