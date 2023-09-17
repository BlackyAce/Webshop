$(document).ready(function() {
    $.ajax({
      url: 'http://localhost:8080/admin/userList', // Ersetzen Sie 'backend-url-here' durch die tatsächliche URL zu Ihrer Backend-Schnittstelle
      headers: { "Authorization": sessionStorage.getItem("token") },
      success: function(response) {
        addUsersToPage(response);
      },
      error: function(error) {
        console.error(error);
      }
    });
  
    function addUsersToPage(data) {
      var users = data.users;
      var tbody = $('#userTableBody');
  
      users.forEach(function(user) {
        var row = $('<tr>');
        row.append($('<td>').text(user.id));
        row.append($('<td>').text(user.username));
        row.append($('<td>').text(user.email));
        row.append($('<td>').text(user.admin ? 'Yes' : 'No'));
        tbody.append(row);
      });
    }
  });
  