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
        userTableBody.append(row);
      });
    }
  });
  