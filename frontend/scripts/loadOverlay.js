document.addEventListener('DOMContentLoaded', function () {
  fetch('overlay.html')
    .then(response => response.text())
    .then(data => {
      document.querySelector('overlay').innerHTML = data;

      checkTokenValidity();

      var loginButton = document.getElementById('loginButtonOverlay');
      if (loginButton) {
        var logoutButton = document.createElement('button');
        logoutButton.id = 'logoutButtonOverlay';
        logoutButton.type = 'button';
        logoutButton.className = 'btn btn-outline-secondary ms-3';
        logoutButton.style = 'color: #f3c5a6';
        logoutButton.innerHTML = 'Logout';

        logoutButton.addEventListener('click', function () {
          sessionStorage.removeItem('token');
          sessionStorage.removeItem('loginTimestamp');
          console.log('Token und Login-Zeitstempel wurden gelöscht');
          var currentPage = window.location.pathname; // Aktuelle Seite ermitteln
          if (currentPage === '/frontend/index.html') {
            window.location.reload(); // Seite neu laden, wenn sich auf der Indexseite befindet
          } else {
            window.location.href = '/frontend/index.html'; // Auf die Indexseite umleiten, wenn sich auf einer anderen Seite befindet
          }
          // Hier kannst du ggf. weitere Aktionen nach dem Abmelden ausführen
        });


        var isAdmin = checkAdminStatus();
        if (isAdmin) {
          var adminLink = document.getElementById('admin');
          if (adminLink) {
            adminLink.href = 'admin.html';
            adminLink.innerHTML = "<a class='nav-link dropdown-toggle text-white' role='button' data-bs-toggle='dropdown' aria-expanded='false'>Admin</a><ul class='dropdown-menu dropdown-menu-dark'><li><a class='dropdown-item' href='/frontend/admin.html'>Create new product</a></li><li><a class='dropdown-item' href='/frontend/UserList.html'>User List</a></li><li><a class='dropdown-item' href='/frontend/ItemList.html'>Product List</a></li><li><a class='dropdown-item' href='/frontend/others.html'>Others</a></li></ul>";
          }
        }
       
        replaceButtons(loginButton, logoutButton); // Funktion zum Austausch der Buttons aufrufen
      } else {
        console.log('Das Element mit der ID "loginButtonOverlay" wurde nicht gefunden.');
      }
      var userMenue = document.getElementById('userMenue');
      if (userMenue && window.sessionStorage.getItem('token') !== null) {
        var container = document.createElement('a');
        adminLink.href = 'admin.html';
        adminLink.innerHTML = "<a class='nav-link dropdown-toggle text-white' role='button' data-bs-toggle='dropdown' aria-expanded='false'>Admin</a><ul class='dropdown-menu dropdown-menu-dark'><li><a class='dropdown-item' href='/frontend/admin.html'>Create new product</a></li><li><a class='dropdown-item' href='/frontend/UserList.html'>User List</a></li><li><a class='dropdown-item' href='/frontend/ItemList.html'>Product List</a></li><li><a class='dropdown-item' href='/frontend/others.html'>Others</a></li></ul>";
        userMenue.parentNode.insertBefore(container, userMenue.nextSibling);
      } 
      

    
})});
// var isUser = checkUserStatus();
// if (isUser) {
//   var userLink = document.getElementById('User');
//   if (userLink) {
//     userLink.href = 'index.html';
//     userLink.innerHTML = "<a class='nav-link dropdown-toggle text-white' role='button' data-bs-toggle='dropdown' aria-expanded='false'>Profile</a><ul class='dropdown-menu dropdown-menu-dark'><li><a class='dropdown-item' href='/frontend/admin.html'>Create new product</a></li><li><a class='dropdown-item' href='/frontend/UserList.html'>User List</a></li><li><a class='dropdown-item' href='/frontend/ItemList.html'>Product List</a></li><li><a class='dropdown-item' href='/frontend/others.html'>Others</a></li></ul>";
//   }
// }

function replaceButtons(loginButton, logoutButton) {
  if (window.sessionStorage.getItem("token") !== null) {
    if (loginButton.parentNode) {
      loginButton.parentNode.replaceChild(logoutButton, loginButton);
      console.log('Logout-Button wurde eingefügt');
    } else {
      console.log('Das Elternelement des Login-Buttons wurde nicht gefunden.');
    }
  } else {
    console.log('Token nicht vorhanden, Login-Button wird angezeigt');
  }
}

